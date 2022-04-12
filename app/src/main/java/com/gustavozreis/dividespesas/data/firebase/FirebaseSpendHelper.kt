package com.gustavozreis.dividespesas.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.gustavozreis.dividespesas.data.models.Spend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FirebaseSpendHelper(
    private val firebaseSpendService: FirebaseSpendService
)  {

    suspend fun getSpendList() = firebaseSpendService.getSpendList()

    suspend fun updateSpend(spend: Spend) = firebaseSpendService.updateSpend(spend)

    suspend fun addSpend(spend: Spend) = firebaseSpendService.addSpend(spend)

    suspend fun deleteSpend() = firebaseSpendService.deleteSpend()


}