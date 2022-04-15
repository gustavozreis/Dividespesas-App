package com.gustavozreis.dividespesas.data.firebase

import com.gustavozreis.dividespesas.data.models.Spend

interface FirebaseSpendService {

    suspend fun getSpendList(): List<Spend>

    suspend fun updateSpend(spend: Spend)

    suspend fun addSpend(spend: Spend, databasePath: String)

    suspend fun deleteSpend()

}