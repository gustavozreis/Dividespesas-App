package com.gustavozreis.dividespesas.data.repository

import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.models.Spend

class SpendRepository(private val helper: FirebaseSpendHelper) {

    suspend fun getSpendList(): List<Spend> = helper.getSpendList()

    suspend fun updateSpend(spend: Spend) = helper.updateSpend(spend)

    suspend fun addSpend(spend: Spend) = helper.addSpend(spend)

    suspend fun deleteSpend() = helper.deleteSpend()

}