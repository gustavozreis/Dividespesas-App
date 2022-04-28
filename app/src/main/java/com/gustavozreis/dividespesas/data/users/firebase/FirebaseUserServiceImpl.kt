package com.gustavozreis.dividespesas.data.users.firebase

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.gustavozreis.dividespesas.R

class FirebaseUserServiceImpl : FirebaseUserService {

    override suspend fun getUserEmail(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email
    }

    override suspend fun getUserId(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.uid
    }

    override suspend fun createUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
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
}