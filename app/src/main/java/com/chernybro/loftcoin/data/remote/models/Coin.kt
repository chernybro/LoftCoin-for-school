package com.chernybro.loftcoin.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Coin(
    val id: Int,
    val name: String,
    val symbol: String,
    @Json(name = "cmc_rank")
    val rank: String,
    val quote: Map<String, Quote>
)

@JsonClass(generateAdapter = true)
data class Quote(
    val price: Double,
    @Json(name = "percent_change_24h")
    val change24h: Double
)
