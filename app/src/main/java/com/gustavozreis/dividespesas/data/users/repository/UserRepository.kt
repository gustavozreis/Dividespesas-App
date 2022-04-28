package com.gustavozreis.dividespesas.data.users.repository

import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper

class UserRepository(private val helper: FirebaseUserHelper) {

    suspend fun getUserEmail(): String? = helper.getUserEmail()

    suspend fun getUserId(): String? = helper.getUserId()

    suspend fun createUser(email: String, password: String) = helper.createUser(email, password)

    suspend fun loginUser(email: String, password: String): Boolean = helper.loginUser(email, password)

}