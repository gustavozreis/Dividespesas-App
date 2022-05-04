package com.gustavozreis.dividespesas.data.utils

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFirestoreInstance {

    var instance = FirebaseFirestore.getInstance()

}