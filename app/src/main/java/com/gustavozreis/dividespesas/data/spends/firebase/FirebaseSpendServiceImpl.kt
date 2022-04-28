package com.gustavozreis.dividespesas.data.spends.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.users.UserInstanceMock
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseSpendServiceImpl : FirebaseSpendService {

    override suspend fun getSpendList(): List<Spend> {
        return suspendCoroutine { continuation ->
            var spendList: MutableList<Spend> = mutableListOf()

            val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

            val documentReference = firebaseFirestore.collection(COUPLES_DATABASE)
                .document(UserInstanceMock.userList[0].userMainDatabaseDocumentId)
                .collection(UserInstanceMock.userList[0].userMainDatabaseCollectionId)

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

    override suspend fun addSpend(spend: Spend, userCollectionPath: String, userDocumentPath: String) {

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val uniqueId = UUID.randomUUID().toString()

        val documentReference = firebaseFirestore.collection(COUPLES_DATABASE)
            .document(UserInstanceMock.userList[0].userMainDatabaseDocumentId)
            .collection(UserInstanceMock.userList[0].userMainDatabaseCollectionId)
            .document(spend.spendId)

        documentReference.set(spend)
    }

    override suspend fun deleteSpend(userDatabasePath: String, spendId: String) {
        val firebaseFirestore = FirebaseFirestore.getInstance()

        val documentReference = firebaseFirestore.collection(COUPLES_DATABASE)
            .document(UserInstanceMock.userList[0].userMainDatabaseDocumentId)
            .collection(UserInstanceMock.userList[0].userMainDatabaseCollectionId)
            .document(spendId)
            .delete()
    }

}