package com.gustavozreis.dividespesas.data.users

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.gustavozreis.dividespesas.data.users.firebase.USERS_DATABASE
import com.gustavozreis.dividespesas.data.users.model.User
import com.gustavozreis.dividespesas.data.utils.FirebaseFirestoreInstance

object UserInstance {

    var currentUser: User? = null

    fun createUser(user: User?) {

        currentUser = User(
            user!!.userMainDatabaseCollectionId,
            user.userMainDatabaseDocumentId,
            user.userSecondaryDatabaseCollectionId,
            user.userSecondaryDatabaseDocumentId,
            user.userEmail,
            user.userFirstName,
            user.userLastName,
            user.userId
        )

    }



}