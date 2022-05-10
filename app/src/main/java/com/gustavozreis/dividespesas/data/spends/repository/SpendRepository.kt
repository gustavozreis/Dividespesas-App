package com.gustavozreis.dividespesas.data.spends.repository

import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.models.Spend

class SpendRepository(private val helper: FirebaseSpendHelper) {

    suspend fun getSpendList(): List<Spend> = helper.getSpendList()

    suspend fun updateSpend(
        spendId: String,
        spend: Spend,
    ) = helper.updateSpend(
        spendId,
        spend)

    suspend fun addSpend(
        spend: Spend,
        userCollectionPath: String,
        userDocumentPath: String,
    ) = helper.addSpend(spend, userCollectionPath, userDocumentPath)

    suspend fun deleteSpend(
        userDatabasePath: String,
        spendId: String,
    ) = helper.deleteSpend(userDatabasePath,
        spendId)

}