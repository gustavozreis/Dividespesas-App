package com.gustavozreis.dividespesas.data.spends.firebase

import com.gustavozreis.dividespesas.data.spends.models.Spend

interface FirebaseSpendService {

    suspend fun getSpendList(): List<Spend>

    suspend fun updateSpend(spend: Spend)

    suspend fun addSpend(spend: Spend, userCollectionPath: String, userDocumentPath: String)

    suspend fun deleteSpend()

}