package com.gustavozreis.dividespesas.data.spends.firebase

import com.gustavozreis.dividespesas.data.spends.models.Spend

class FirebaseSpendHelper(
    private val firebaseSpendService: FirebaseSpendService
)  {

    suspend fun getSpendList() = firebaseSpendService.getSpendList()

    suspend fun updateSpend(spend: Spend) = firebaseSpendService.updateSpend(spend)

    suspend fun addSpend(spend: Spend,
                         userCollectionPath: String,
                         userDocumentPath: String)
    = firebaseSpendService.addSpend(spend, userCollectionPath, userDocumentPath)

    suspend fun deleteSpend(userDatabasePath: String,
                            spendId: String)
    = firebaseSpendService.deleteSpend(userDatabasePath, spendId)


}