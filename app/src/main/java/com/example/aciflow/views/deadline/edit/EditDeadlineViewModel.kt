package com.example.aciflow.views.deadline.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.AppState
import com.example.aciflow.model.DeadlinePriority
import com.example.aciflow.model.DeadlineTag
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.receiver.DeadlineReminderReceiver
import com.example.aciflow.views.AciFlowViewModel
import com.google.firebase.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EditDeadlineViewModel(
    private val accountService: AccountService,
    private val storageService: StorageService,
    appState: AppState
) : AciFlowViewModel() {

    private val _uiState = MutableStateFlow(EditDeadlineUIState())
    val uiState: StateFlow<EditDeadlineUIState> = _uiState.asStateFlow()

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateDueDate(context: Context, newDueDate: Date) {
        val deadlineMillis = newDueDate.time
        val reminderReceiver = DeadlineReminderReceiver()
        reminderReceiver.scheduleReminder(context, deadlineMillis)
        _uiState.value = _uiState.value.copy(dueDate = newDueDate)
    }

    fun updateReminder(newReminder: Timestamp) {
        _uiState.value = _uiState.value.copy(reminder = newReminder)
    }

    fun updateDescription(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun updateTag(newTag: DeadlineTag) {
        _uiState.value = _uiState.value.copy(tag = newTag)
    }

    fun updatePriority(newPriority: DeadlinePriority) {
        _uiState.value = _uiState.value.copy(priority = newPriority)
    }

    fun saveDeadline(context: Context) {
        val deadlineMillis = _uiState.value.dueDate.time
        val reminderReceiver = DeadlineReminderReceiver()
        reminderReceiver.scheduleReminder(context, deadlineMillis)
        CoroutineScope(Dispatchers.IO).launch {
            storageService.addDeadline(
                accountService.currentUserId,
                _uiState.value.title,
                _uiState.value.description,
                _uiState.value.dueDate,
                _uiState.value.tag,
                _uiState.value.priority
            )
        }
    }
}

class EditDeadlineViewModelFactory(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val appState: AppState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditDeadlineViewModel::class.java)) {
            return EditDeadlineViewModel(
                accountService = accountService,
                storageService = storageService,
                appState = appState
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
