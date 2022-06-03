package com.chernybro.loftcoin.ui.activity.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chernybro.loftcoin.ui.data.Rate

class RatesViewModel: ViewModel() {

    private val _rates: MutableLiveData<List<Rate>> = MutableLiveData()
    val rates: LiveData<List<Rate>> = _rates

    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    val isRefreshing: LiveData<Boolean> = _isRefreshing
}