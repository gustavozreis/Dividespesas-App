package com.gustavozreis.dividespesas.features.utils

import java.text.DecimalFormat

class CurrencyFormater {

    fun doubleToString(value: Double): String {
        val valueDouble = DecimalFormat("0.00").format(value)
        val valueString = valueDouble.toString()
        val valueStringFinal = "${valueString.replace(".", ",")}"
        return valueStringFinal
    }

}