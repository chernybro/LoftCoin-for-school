package com.chernybro.loftcoin.data.service.coins

import android.util.Log
import com.chernybro.loftcoin.BuildConfig
import com.chernybro.loftcoin.data.models.Coin
import com.chernybro.loftcoin.data.models.Listings
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException


class CoinsRepoImpl : CoinsRepo {
    private val api: ListingsApi

    init {
        api = createRetrofit(createHttpClient()).create(ListingsApi::class.java)
    }


    override fun listings(currency: String): List<Coin> {
        return try {
            val response: Response<Listings> = api.getListings(currency).execute()
            if (response.isSuccessful) {
                val listings = response.body()
                listings?.data ?: emptyList()
            } else {
                val responseBody = response.errorBody()
                if (responseBody != null) {
                    throw IOException(responseBody.string())
                } else {
                    throw Exception("UnknownException")
                }
            }
        } catch (e: Exception) {
            Timber.d("listings ${e.localizedMessage}")
            Log.e("TAG", "${e.localizedMessage}")
            e.printStackTrace()
            emptyList()
        } finally {
            emptyList<Coin>()
        }
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            chain.proceed(
                request.newBuilder()
                    .addHeader(ListingsApi.API_KEY, BuildConfig.API_KEY)
                    .build()
            )
        })
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            interceptor.redactHeader(BuildConfig.API_KEY)
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(httpClient)
        builder.baseUrl(BuildConfig.API_ENDPOINT)
            builder.addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
        return builder.build()
    }
}