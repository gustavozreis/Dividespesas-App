package com.gustavozreis.dividespesas.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.gustavozreis.dividespesas.data.models.Spend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseSpendServiceImpl : FirebaseSpendService {

    override suspend fun getSpendList(): List<Spend> {
        return suspendCoroutine { continuation ->
            var spendList: MutableList<Spend> = mutableListOf()

            val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

            val documentReference = firebaseFirestore.collection(COUPLES_DATABASE)
                .document("kllj8b8by5DFGMpAW6SK")
                .collection("spend01273012730712")

            documentReference.get().addOnSuccessListener { spends ->
                for (spend in spends) {
                    val spendCreated = Spend(
                        spend.getString("spendDate").toString(),
                        spend.getString("spendDescription").toString(),
                        spend.getString("spendId").toString(),
                        spend.getString("spendType").toString(),
                        spend.getString("spendUser").toString(),
                        spend.getDouble("spendValue")!!.toDouble()
                    )
                    spendList.add(spendCreated)
                }
                continuation.resumeWith(Result.success(spendList))
            }

            documentReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }
    }

    override suspend fun updateSpend(spend: Spend) {
        TODO("Not yet implemented")
    }

    override suspend fun addSpend(spend: Spend, databasePath: String) {

        val uniqueIdMock = UUID.randomUUID().toString()

        /*spend = Spend(
            "11/02/2022",
            "Compra Fraldas",
            uniqueIdMock,
            "Teste Tipo",
            "Gustavo",
            20.02
        )*/

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val uniqueId = UUID.randomUUID().toString()

        val documentReference = firebaseFirestore.collection(COUPLES_DATABASE)
            .document("kllj8b8by5DFGMpAW6SK")
            .collection("spend01273012730712")
            .document(uniqueId)

        documentReference.set(spend)
    }

    override suspend fun deleteSpend() {
        TODO("Not yet implemented")
    }

}