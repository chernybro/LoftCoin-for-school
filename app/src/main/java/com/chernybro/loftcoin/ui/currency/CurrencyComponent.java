package com.chernybro.loftcoin.ui.currency;

import androidx.lifecycle.ViewModelProvider;

import com.chernybro.loftcoin.BaseComponent;
import com.chernybro.loftcoin.utils.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    CurrencyModule.class,
    ViewModelModule.class
}, dependencies = {
    BaseComponent.class
})
abstract class CurrencyComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

}
