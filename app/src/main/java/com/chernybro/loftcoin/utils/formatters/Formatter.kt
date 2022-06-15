package com.chernybro.loftcoin.utils.formatters

interface Formatter<T> {
    fun format(value: T): String
}