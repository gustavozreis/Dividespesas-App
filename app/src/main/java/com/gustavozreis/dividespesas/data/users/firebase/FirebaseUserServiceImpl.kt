package com.gustavozreis.dividespesas.data.users.firebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import com.google.firebase.auth.FirebaseAuth
import com.gustavozreis.dividespesas.data.spends.firebase.COUPLES_DATABASE
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.users.UserInstance
import com.gustavozreis.dividespesas.data.users.model.User
import com.gustavozreis.dividespesas.data.utils.FirebaseFirestoreInstance
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseUserServiceImpl : FirebaseUserService {

    override suspend fun getUserEmail(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email
    }

    override suspend fun getUserId(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.uid
    }

    override suspend fun createUser(
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String,
    ) {

        val userId = UUID.randomUUID().toString()

        val userMainDatabaseCollectionId = UUID.randomUUID().toString()
        val userMainDatabaseDocumentId = UUID.randomUUID().toString()
        val userSecondaryDatabaseCollectionId = UUID.randomUUID().toString()
        val userSecondaryDatabaseDocumentId = UUID.randomUUID().toString()

        val userObject = User(
            userMainDatabaseCollectionId,
            userMainDatabaseDocumentId,
            userSecondaryDatabaseCollectionId,
            userSecondaryDatabaseDocumentId,
            userEmail,
            userFirstName,
            userLastName,
            userId
        )

        val documentReference = FirebaseFirestoreInstance.instance.collection(USERS_DATABASE)
            .document(userObject.userId)

        documentReference.set(userObject)
    }

    override suspend fun loginUser(email: String, password: String): Boolean {
        val auth = FirebaseAuth.getInstance()
        var result: Boolean = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { auth ->
                if (auth.isSuccessful) {
                    result = true
                }
            }.addOnFailureListener { e ->
                result = false
            }

        return result
    }

    override suspend fun getUserFromEmail(): User? {

        return suspendCoroutine { continuation ->

            val auth = FirebaseAuth.getInstance()
            val userEmail = auth.currentUser?.email

            var currentUser: User? = null

            val database = FirebaseFirestoreInstance.instance

            database
                .collection(USERS_DATABASE)
                .whereEqualTo("userEmail", userEmail)
                .get()
                .addOnSuccessListener { documentList ->
                    for (user in documentList) {
                        if (user.getString("userEmail") == userEmail) {
                            currentUser = User(
                                user.getString("userMainDatabaseCollectionId").toString(),
                                user.getString("userMainDatabaseDocumentId").toString(),
                                user.getString("userSecondaryDatabaseCollectionId").toString(),
                                user.getString("userSecondaryDatabaseDocumentId").toString(),
                                user.getString("userEmail").toString(),
                                user.getString("userFirstName").toString(),
                                user.getString("userLastName").toString(),
                                user.getString("userId").toString()
                            )
                        }
                        continuation.resumeWith(Result.success(currentUser))
                    }

                }.addOnFailureListener { exception ->
                    Log.w(TAG, "ERROAQUI $exception")
                    continuation.resumeWith(Result.failure(exception))

                }
        }


    }
}