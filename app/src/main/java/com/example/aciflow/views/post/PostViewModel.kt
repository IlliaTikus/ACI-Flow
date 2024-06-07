package com.example.aciflow.views.post

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PostViewModel : ViewModel() {
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun publishPost() {
        // Implement publish logic here
    }
}
