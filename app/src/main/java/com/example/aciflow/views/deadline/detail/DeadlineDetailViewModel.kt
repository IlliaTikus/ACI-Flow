package com.example.aciflow.views.deadline.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Deadline(val title: String, val date: String, val description: String)

class DeadlineDetailViewModel : ViewModel() {
    private val deadlines = listOf(
        Deadline("Project X", "27.05.2024", "Description for Project X"),
        Deadline("IoT Exam", "04.06.2024", "Study for the IoT exam.")
    )

    fun getDeadline(title: String): StateFlow<Deadline?> {
        val deadline = deadlines.find { it.title == title }
        return MutableStateFlow(deadline)
    }
}
