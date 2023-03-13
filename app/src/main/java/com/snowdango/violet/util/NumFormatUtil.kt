package com.snowdango.violet.util

import android.icu.text.DecimalFormat
import java.math.RoundingMode

fun Number.toShortString(): String {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    var num = this.toDouble()
    var digits = 0
    while (true) {
        if (num < 1000) break
        num /= 1000
        digits++
    }
    val formatter = DecimalFormat("###.#").also {
        it.roundingMode = RoundingMode.DOWN.ordinal
    }
    return formatter.format(num) + suffix[digits]
}