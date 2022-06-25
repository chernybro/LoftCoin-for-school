package com.chernybro.loftcoin.data.remote.service.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chernybro.loftcoin.data.local.database.LoftDatabase
import com.chernybro.loftcoin.data.local.entities.RoomCoin
import com.chernybro.loftcoin.data.remote.models.CmcCoin
import com.chernybro.loftcoin.data.remote.models.Coin
import com.chernybro.loftcoin.data.remote.models.Listings
import com.chernybro.loftcoin.data.remote.models.SortBy
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CoinsRepoImpl @Inject constructor(
    private val api: ListingsApi,
    private val db: LoftDatabase,
    private val executor: ExecutorService
) : CoinsRepo {

    override fun listings(query: CoinsRepo.Query): LiveData<List<Coin>> {
        fetchFromNetworkIfNecessary(query)
        try {
            return fetchFromDb(query)
        } catch (e: Exception) {
            return fetchFromDb(query)
        }
    }

    private fun fetchFromDb(query: CoinsRepo.Query): LiveData<List<Coin>> {
        val coins: LiveData<List<RoomCoin>>
        coins = if (query.sortBy == SortBy.PRICE) {
            db.coins().fetchAllSortByPrice()
        } else {
            db.coins().fetchAllSortByRank()
        }
        return Transformations.map<List<RoomCoin>, List<Coin>>(coins) { roomCoins ->
            roomCoins.map {
                Coin(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    rank = it.rank,
                    price = it.price,
                    change24h = it.change24h,
                    currencyCode = it.currencyCode
                )
            }
        }
    }

    private fun fetchFromNetworkIfNecessary(query: CoinsRepo.Query) {
        executor.submit {
            if (query.forceUpdate || db.coins().coinsCount() == 0) {
                try {
                    val response: Response<Listings> =
                        api.getListings(query.currency).execute()
                    if (response.isSuccessful) {
                        val listings = response.body()
                        if (listings != null) {
                            saveCoinsIntoDb(query, listings.data)
                        }
                    } else {
                        val responseBody = response.errorBody()
                        if (responseBody != null) {
                            throw IOException(responseBody.string())
                        }
                    }
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun saveCoinsIntoDb(query: CoinsRepo.Query, coins: List<CmcCoin>) {
        val roomCoins: MutableList<RoomCoin> = java.util.ArrayList<RoomCoin>(coins.size)
        for (coin in coins) {
            roomCoins.add(
                RoomCoin(
                    name = coin.name,
                    symbol = coin.symbol,
                    rank = coin.rank,
                    price = coin.quote.values.iterator().next().price,
                    change24h = coin.quote.values.iterator().next().change24h,
                    currencyCode = query.currency,
                    id = coin.id
                )
            )
        }
        db.coins().insert(roomCoins)
    }

}