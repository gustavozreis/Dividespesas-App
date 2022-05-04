package com.gustavozreis.dividespesas.data.users.firebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import com.google.firebase.auth.FirebaseAuth
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

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(userEmail, userPassword)

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

    override suspend fun getUserFromEmail(email: String): User? {

        return suspendCoroutine { continuation ->

            val auth = FirebaseAuth.getInstance()
            val userEmail = auth.currentUser?.email

            var currentUser: User? = User(
                "spend01273012730712",
                "kllj8b8by5DFGMpAW6SK",
                "1243f432fg",
                "1241qc4tc2t4",
                "gzreis@gmail.com",
                "Gustavo",
                "Reis",
                "q892ynpybqcx924bc343w",

            )

            val collectionReference = FirebaseFirestoreInstance.instance.collection(USERS_DATABASE)

            continuation.resumeWith(Result.success(currentUser))


           collectionReference.get()
                .addOnSuccessListener { documentList ->
                    for (user in documentList) {
                        if (user.getString("userEmail") == userEmail) {
                            currentUser = User(
                                user.getString("userMainDatabaseCollectionId")!!,
                                user.getString("userMainDatabaseDocumentId")!!,
                                user.getString("userSecondaryDatabaseCollectionId")!!,
                                user.getString("userSecondaryDatabaseDocumentId")!!,
                                user.getString("userEmail")!!,
                                user.getString("userFirstName")!!,
                                user.getString("userLastName")!!,
                                user.getString("userId")!!,
                            )
                        }
                        continuation.resumeWith(Result.success(currentUser))
                    }

                }

            collectionReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.success(currentUser))

            }
        }


    }
}