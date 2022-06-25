package com.chernybro.loftcoin.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currency(
    val symbol: String,
    val code: String,
    val name: String
)