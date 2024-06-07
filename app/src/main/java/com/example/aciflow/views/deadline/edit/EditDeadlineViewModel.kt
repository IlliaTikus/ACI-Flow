package com.example.aciflow.views.deadline.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditDeadlineViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDate(newDate: String) {
        _date.value = newDate
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun saveDeadline() {
        // Implement save logic here
    }
}
