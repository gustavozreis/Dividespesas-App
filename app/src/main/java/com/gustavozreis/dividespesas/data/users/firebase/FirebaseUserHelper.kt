package com.gustavozreis.dividespesas.data.users.firebase

import com.gustavozreis.dividespesas.data.users.model.User

class FirebaseUserHelper(private val firebaseUserService: FirebaseUserService) {

    suspend fun getUserEmail() = firebaseUserService.getUserEmail()

    suspend fun getUserId() = firebaseUserService.getUserId()

    suspend fun createUser(
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String,
    ) = firebaseUserService.createUser(
        userEmail,
        userPassword,
        userFirstName,
        userLastName,
    )

    suspend fun loginUser(email: String, password: String) =
        firebaseUserService.loginUser(email, password)

    suspend fun getUserFromEmail (): User? =
        firebaseUserService.getUserFromEmail()

}