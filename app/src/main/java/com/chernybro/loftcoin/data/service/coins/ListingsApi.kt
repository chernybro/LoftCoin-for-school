package com.chernybro.loftcoin.data.service.coins

import com.chernybro.loftcoin.data.models.Listings
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ListingsApi {
    @GET("cryptocurrency/listings/latest")
    fun getListings(@Query("convert") convert: String): Call<Listings>

    companion object {
        const val API_KEY = "X-CMC_PRO_API_KEY"
    }
}