package com.chernybro.loftcoin.ui.rates;

import androidx.lifecycle.ViewModelProvider;


import com.chernybro.loftcoin.BaseComponent;
import com.chernybro.loftcoin.utils.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    RatesModule.class,
    ViewModelModule.class
}, dependencies = {
    BaseComponent.class
})
abstract class RatesComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

    abstract RatesAdapter ratesAdapter();

}
