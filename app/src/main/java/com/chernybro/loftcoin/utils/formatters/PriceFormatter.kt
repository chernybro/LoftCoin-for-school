package com.chernybro.loftcoin.utils.formatters

import android.icu.text.NumberFormat
import android.os.Build

class PriceFormatter : Formatter<Double> {
    override fun format(value: Double): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NumberFormat.getCurrencyInstance().format(value)
        } else {
            java.text.NumberFormat.getCurrencyInstance().format(value)
        }
    }
}