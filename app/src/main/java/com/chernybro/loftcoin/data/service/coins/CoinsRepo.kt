package com.chernybro.loftcoin.data.service.coins

import androidx.annotation.WorkerThread
import com.chernybro.loftcoin.data.models.Coin

interface CoinsRepo {

    @WorkerThread
    fun listings(currency: String): List<Coin>

}