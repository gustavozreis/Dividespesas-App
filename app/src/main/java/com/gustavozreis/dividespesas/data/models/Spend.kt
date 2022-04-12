package com.gustavozreis.dividespesas.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Spend(
    val spendDate: String = "",
    val spendDescription: String = "",
    val spendId: String = "",
    val spendType: String = "",
    val spendUser: String = "",
    val spendValue: Double = 0.0
) : Parcelable
