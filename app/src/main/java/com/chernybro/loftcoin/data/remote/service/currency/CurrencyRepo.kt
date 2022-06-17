package com.chernybro.loftcoin.data.remote.service.currency

import androidx.lifecycle.LiveData
import com.chernybro.loftcoin.data.remote.models.Currency

interface CurrencyRepo {
    fun availableCurrencies(): LiveData<List<Currency>>
    fun currency(): LiveData<Currency>
    fun updateCurrency(currency: Currency)
}