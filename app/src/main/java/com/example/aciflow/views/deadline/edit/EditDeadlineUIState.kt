package com.example.aciflow.views.deadline.edit

import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
import com.google.firebase.Timestamp
import java.util.Date

data class EditDeadlineUIState(
    val title: String = "",
    val description: String = "",
    val dueDate: Date = Date(),
    val tag: DeadlineTag? = null,
    val priority: DeadlinePriority? = null,
)
