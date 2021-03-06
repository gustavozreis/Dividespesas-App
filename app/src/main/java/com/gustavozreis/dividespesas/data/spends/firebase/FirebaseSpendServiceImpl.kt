package com.gustavozreis.dividespesas.data.spends.firebase

import com.firebase.ui.auth.data.model.User
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.users.UserInstance
import com.gustavozreis.dividespesas.data.utils.FirebaseFirestoreInstance
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseSpendServiceImpl : FirebaseSpendService {

    override suspend fun getSpendList(): List<Spend> {

        return suspendCoroutine { continuation ->
            var spendList: MutableList<Spend> = mutableListOf()

            val documentReference = FirebaseFirestoreInstance.instance.collection(COUPLES_DATABASE)
                .document(UserInstance.currentUser!!.userMainDatabaseDocumentId)
                .collection(UserInstance.currentUser!!.userMainDatabaseCollectionId)

            documentReference.get().addOnSuccessListener { spends ->
                for (spend in spends) {
                    val spendCreated = Spend(
                        spend.getString("spendDate").toString(),
                        spend.getString("spendDescription").toString(),
                        spend.getString("spendId").toString(),
                        spend.getString("spendType").toString(),
                        spend.getString("spendUser").toString(),
                        spend.getDouble("spendValue")!!.toDouble(),
                        spend.getDouble("spendIndex")!!.toInt()
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

    override suspend fun updateSpend(spendId: String, spend: Spend) {
        val documentReference = FirebaseFirestoreInstance.instance.collection(COUPLES_DATABASE)
            .document(UserInstance.currentUser!!.userMainDatabaseDocumentId)
            .collection(UserInstance.currentUser!!.userMainDatabaseCollectionId)
            .document(spendId)

        documentReference.set(spend)
    }

    override suspend fun addSpend(
        spend: Spend,
        userCollectionPath: String,
        userDocumentPath: String,
    ) {

        val uniqueId = UUID.randomUUID().toString()

        val documentReference = FirebaseFirestoreInstance.instance.collection(COUPLES_DATABASE)
            .document(UserInstance.currentUser!!.userMainDatabaseDocumentId)
            .collection(UserInstance.currentUser!!.userMainDatabaseCollectionId)
            .document(spend.spendId)

        documentReference.set(spend)
    }

    override suspend fun deleteSpend(userDatabasePath: String, spendId: String) {

        val documentReference = FirebaseFirestoreInstance.instance.collection(COUPLES_DATABASE)
            .document(UserInstance.currentUser!!.userMainDatabaseDocumentId)
            .collection(UserInstance.currentUser!!.userMainDatabaseCollectionId)
            .document(spendId)
            .delete()
    }

}