package com.chernybro.loftcoin.utils.formatters

import java.util.*
import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class PercentFormatter @Inject internal constructor() : Formatter<Double> {

    override fun format(value: Double): String {
        return String.format(Locale.US, "%.2f%%", value)
    }
}