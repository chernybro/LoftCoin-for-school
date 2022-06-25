package com.chernybro.loftcoin;

import android.content.Context;

import com.chernybro.loftcoin.data.remote.service.coins.CoinsRepo;
import com.chernybro.loftcoin.data.remote.service.currency.CurrencyRepo;
import com.chernybro.loftcoin.utils.image_loader.ImageLoader;


public interface BaseComponent {
    Context context();
    CoinsRepo coinsRepo();
    CurrencyRepo currencyRepo();
    ImageLoader imageLoader();
}
