package com.example.aciflow.views.deadlines

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Deadline(val title: String, val date: String, val isUrgent: Boolean)

class DeadlinesViewModel : ViewModel() {

    private val _deadlines = MutableStateFlow(listOf(
        Deadline("Project X", "27.05.2024", false),
        Deadline("IoT Exam", "04.06.2024", true)
    ))
    val deadlines: StateFlow<List<Deadline>> = _deadlines
}
