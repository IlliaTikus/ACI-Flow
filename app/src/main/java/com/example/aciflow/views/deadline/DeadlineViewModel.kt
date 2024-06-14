package com.example.aciflow.views.deadline

import androidx.lifecycle.ViewModel
import com.example.aciflow.model.Deadline
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO: fixen, so wie die anderen viewmodels
class DeadlinesViewModel : ViewModel() {
    private val _deadlines = MutableStateFlow(emptyList<Deadline>())
    val deadlines: StateFlow<List<Deadline>> = _deadlines
}
