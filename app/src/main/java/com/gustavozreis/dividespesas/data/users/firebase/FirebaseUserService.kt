package com.gustavozreis.dividespesas.data.users.firebase

interface FirebaseUserService {

    suspend fun getUserEmail(): String?

    suspend fun getUserId(): String?

    suspend fun createUser(email: String, password: String)

    suspend fun loginUser(email: String, password: String): Boolean

}