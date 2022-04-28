package com.gustavozreis.dividespesas.data.users.firebase

class FirebaseUserHelper(private val firebaseUserService: FirebaseUserService) {

    suspend fun getUserEmail() = firebaseUserService.getUserEmail()

    suspend fun getUserId() = firebaseUserService.getUserId()

    suspend fun createUser(email: String, password: String) = firebaseUserService.createUser(email, password)

    suspend fun loginUser(email: String, password: String) = firebaseUserService.loginUser(email, password)

}