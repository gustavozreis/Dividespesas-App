package com.gustavozreis.dividespesas.data.users.firebase

import com.gustavozreis.dividespesas.data.users.model.User

interface FirebaseUserService {

    suspend fun getUserEmail(): String?

    suspend fun getUserId(): String?

    suspend fun createUser(
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String,
    )

    suspend fun loginUser(email: String, password: String): Boolean

    suspend fun getUserFromEmail (): User?

}