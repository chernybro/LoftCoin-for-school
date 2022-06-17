package com.chernybro.loftcoin.data.remote.service.currency

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.chernybro.loftcoin.R
import com.chernybro.loftcoin.data.remote.models.Currency
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CurrencyRepoImpl @Inject constructor(context: Context) : CurrencyRepo {
    private val availableCurrencies: MutableMap<String?, Currency> = HashMap()
    private val prefs: SharedPreferences

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        availableCurrencies["USD"] = Currency("$", "USD", context.getString(R.string.usd))
        availableCurrencies["EUR"] = Currency("E", "EUR", context.getString(R.string.eur))
        availableCurrencies["RUB"] = Currency("R", "RUB", context.getString(R.string.rub))
    }

    companion object {
        private const val KEY_CURRENCY = "currency"
    }


    override fun availableCurrencies(): LiveData<List<Currency>> {
        val liveData = MutableLiveData<List<Currency>>()
        liveData.value = ArrayList(availableCurrencies.values)
        return liveData
    }

    override fun currency(): LiveData<Currency> {
        return CurrencyLiveData()
    }

    override fun updateCurrency(currency: Currency) {
        prefs.edit().putString(KEY_CURRENCY, currency.code).apply()
    }

    private inner class CurrencyLiveData : LiveData<Currency>(), OnSharedPreferenceChangeListener {
        override fun onActive() {
            prefs.registerOnSharedPreferenceChangeListener(this)
            value = availableCurrencies[prefs.getString(KEY_CURRENCY, "USD")]
        }

        override fun onInactive() {
            prefs.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(prefs: SharedPreferences, key: String) {
            value = availableCurrencies[prefs.getString(key, "USD")]
        }
    }

}