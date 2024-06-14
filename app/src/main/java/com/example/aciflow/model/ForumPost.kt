package com.example.aciflow.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class ForumPost(
    //user UID of author user
    val author: DocumentReference? = null,
    val createdAt: Timestamp? = null,
    val content: String? = null,
    val authorName: String? = null,
)
