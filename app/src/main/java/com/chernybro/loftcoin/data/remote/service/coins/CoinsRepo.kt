package com.chernybro.loftcoin.data.remote.service.coins

import androidx.lifecycle.LiveData
import com.chernybro.loftcoin.data.remote.models.Coin
import com.chernybro.loftcoin.data.remote.models.SortBy


interface CoinsRepo {

    fun listings(query: CoinsRepo.Query): LiveData<List<Coin>>

    data class Query(
        val currency: String,
        val forceUpdate: Boolean,
        val sortBy: SortBy
    ) {
        private constructor(builder: Builder) : this(builder.currency!!, builder.forceUpdate!!, builder.sortBy!!)

        data class Builder(
            var currency: String? = null,
            var forceUpdate: Boolean? = null,
            var sortBy: SortBy? = null) {

            fun currency(currency: String) = apply { this.currency = currency }
            fun forceUpdate(forceUpdate: Boolean) = apply { this.forceUpdate = forceUpdate }
            fun sortBy(sortBy: SortBy) = apply { this.sortBy = sortBy }
            fun build() = Query(this)
        }
    }
}