package com.chernybro.loftcoin.data;

import android.content.Context;

import androidx.room.Room;

import com.chernybro.loftcoin.BuildConfig;
import com.chernybro.loftcoin.data.local.database.LoftDatabase;
import com.chernybro.loftcoin.data.remote.service.coins.CoinsRepo;
import com.chernybro.loftcoin.data.remote.service.coins.CoinsRepoImpl;
import com.chernybro.loftcoin.data.remote.service.coins.ListingsApi;
import com.chernybro.loftcoin.data.remote.service.currency.CurrencyRepo;
import com.chernybro.loftcoin.data.remote.service.currency.CurrencyRepoImpl;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public abstract class DataModule {

    @Provides
    static Moshi moshi() {
        final Moshi moshi = new Moshi.Builder().build();
        return moshi.newBuilder()
            .build();
    }

    @Provides
    static Retrofit retrofit(OkHttpClient httpClient, Moshi moshi) {
        final Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClient.newBuilder()
            .addInterceptor(chain -> {
                final Request request = chain.request();
                return chain.proceed(request.newBuilder()
                    .addHeader(ListingsApi.API_KEY, BuildConfig.API_KEY)
                    .build());
            })
            .build());
        builder.baseUrl(BuildConfig.API_ENDPOINT);
        builder.addConverterFactory( MoshiConverterFactory.create(
                new Moshi.Builder()
                        .add(new KotlinJsonAdapterFactory())
                        .build()
        ));
        return builder.build();
    }

    @Provides
    static ListingsApi listingsApi(Retrofit retrofit) {
        return retrofit.create(ListingsApi.class);
    }

    @Provides
    @Singleton
    static LoftDatabase loftDatabase(Context context) {
        if (BuildConfig.DEBUG) {
            return Room.inMemoryDatabaseBuilder(context, LoftDatabase.class).build();
        } else {
            return Room.databaseBuilder(context, LoftDatabase.class, "loft.db").build();
        }
    }

    @Binds
    abstract CoinsRepo coinsRepo(CoinsRepoImpl impl);

    @Binds
    abstract CurrencyRepo currencyRepo(CurrencyRepoImpl impl);

}
