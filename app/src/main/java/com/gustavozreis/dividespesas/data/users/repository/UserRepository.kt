package com.gustavozreis.dividespesas.data.users.repository

import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.model.User

class UserRepository(private val helper: FirebaseUserHelper) {

    suspend fun getUserEmail(): String? = helper.getUserEmail()

    suspend fun getUserId(): String? = helper.getUserId()

    suspend fun createUser(
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String,
    ) = helper.createUser(
        userEmail,
        userPassword,
        userFirstName,
        userLastName,
    )

    suspend fun loginUser(email: String, password: String): Boolean =
        helper.loginUser(email, password)

    suspend fun getUserFromEmail(email: String): User? =
        helper.getUserFromEmail(email)

}