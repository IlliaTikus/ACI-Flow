package com.example.aciflow.model

import com.google.firebase.firestore.DocumentReference

data class User(
    val username: String? = null,
    val department: DocumentReference? = null,
    val semester: Int? = null,
)
