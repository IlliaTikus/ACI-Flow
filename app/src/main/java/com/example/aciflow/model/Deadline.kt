package com.example.aciflow.model

import com.google.firebase.Timestamp
import java.util.Date

data class Deadline(
    val title: String? = null,
    val description: String? = null,
    val dueDate: Date? = null,
    val tag: String? = null,
    val priority: String? = null,

    val id: String? = null,
)
