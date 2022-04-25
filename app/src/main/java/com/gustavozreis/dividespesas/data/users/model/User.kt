package com.gustavozreis.dividespesas.data.users.model

data class User(
    val userMainDatabaseCollectionId: String = "",
    val userMainDatabaseDocumentId: String = "",
    val userSecondaryDatabaseCollectionId: String = "",
    val userSecondaryDatabaseDocumentId: String = "",
    val userEmail: String = "",
    val userFirstName: String = "",
    val userLastName: String = ""
)
