package com.example.aciflow.views.deadline.edit

import androidx.lifecycle.ViewModel
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditDeadlineViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _priority = MutableStateFlow<DeadlinePriority?>(null)
    val priority: StateFlow<DeadlinePriority?> = _priority

    private val _tag = MutableStateFlow<DeadlineTag?>(null)
    val tag: StateFlow<DeadlineTag?> = _tag

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDate(newDate: String) {
        _date.value = newDate
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun updatePriority(newPriority: DeadlinePriority) {
        _priority.value = newPriority
    }

    fun updateTag(newTag: DeadlineTag) {
        _tag.value = newTag
    }

    fun saveDeadline() {
        // Implement save logic here
    }
}
