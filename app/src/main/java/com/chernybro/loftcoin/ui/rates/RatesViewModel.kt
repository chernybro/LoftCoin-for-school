package com.chernybro.loftcoin.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chernybro.loftcoin.data.remote.models.Coin
import com.chernybro.loftcoin.data.remote.models.SortBy
import com.chernybro.loftcoin.data.remote.service.coins.CoinsRepo
import com.chernybro.loftcoin.data.remote.service.currency.CurrencyRepo
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val coinsRepo: CoinsRepo,
    private val currencyRepo: CurrencyRepo
) : ViewModel() {
    private val isRefreshing = MutableLiveData<Boolean>()

    private val forceRefresh = MutableLiveData(AtomicBoolean(false))

    private val sortBy: MutableLiveData<SortBy> = MutableLiveData<SortBy>(SortBy.RANK)

    private var coins: LiveData<List<Coin>>

    private var sortingIndex = 1

    init {
        val query: LiveData<CoinsRepo.Query> = Transformations.switchMap(forceRefresh) { r: AtomicBoolean ->
            Transformations.switchMap(
                currencyRepo.currency()
            ) { c ->
                r.set(true)
                isRefreshing.postValue(true)
                Transformations.map(sortBy) { s ->
                    CoinsRepo.Query.Builder()
                        .currency(c.code)
                        .forceUpdate(r.getAndSet(false))
                        .sortBy(s)
                        .build()
                }
            }
        }
        val coins: LiveData<List<Coin>> = Transformations.switchMap(query, coinsRepo::listings)
        this.coins = Transformations.map(coins) { c ->
            isRefreshing.postValue(false)
            c
        }
    }

    fun coins(): LiveData<List<Coin>> {
        return coins
    }

    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshing
    }

    fun refresh() {
        forceRefresh.postValue(AtomicBoolean(true))
    }

    fun switchSortingOrder() {
        sortBy.postValue(SortBy.values()[sortingIndex++ % SortBy.values().size])
    }

}