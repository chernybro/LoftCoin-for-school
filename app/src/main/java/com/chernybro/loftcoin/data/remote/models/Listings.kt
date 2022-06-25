package com.chernybro.loftcoin.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listings(
    val data: List<CmcCoin>
)