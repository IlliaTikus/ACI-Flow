package com.example.aciflow.common.snackbar

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SnackbarManager {
    private val messages: MutableStateFlow<String?> = MutableStateFlow(null)
    val snackbarMessages: StateFlow<String?>
        get() = messages.asStateFlow()

    fun showMessage(message: String) {
        messages.value = message
    }

    fun clearSnackbarState() {
        messages.value = null
    }
}
