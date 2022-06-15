package com.chernybro.loftcoin.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chernybro.loftcoin.data.models.Coin
import com.chernybro.loftcoin.data.service.coins.CoinsRepo
import com.chernybro.loftcoin.data.service.coins.CoinsRepoImpl
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RatesViewModel : ViewModel() {
    private val coins = MutableLiveData<List<Coin>>()
    private val isRefreshing = MutableLiveData<Boolean>()
    private val executor = Executors.newSingleThreadExecutor()
    private val repo: CoinsRepo
    private var future: Future<*>? = null

    init {
        repo = CoinsRepoImpl()
        refresh()
    }

    fun coins(): LiveData<List<Coin>> {
        return coins
    }

    fun isRefreshing(): LiveData<Boolean> {
        return isRefreshing
    }

    fun refresh() {
        isRefreshing.postValue(true)
        future = executor.submit {
            coins.postValue(repo.listings("USD"))
            isRefreshing.postValue(false)
        }
    }

    override fun onCleared() {
        if (future != null) {
            future!!.cancel(true)
        }
    }


}