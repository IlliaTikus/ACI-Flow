package com.example.aciflow.views.deadline.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.model.Deadline
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.AciFlowViewModel
import com.example.aciflow.views.deadline.edit.EditDeadlineUIState
import com.example.aciflow.views.deadlines.DeadlinesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DeadlineDetailViewModel(
    private val storageService: StorageService,
    private val accountService: AccountService
) : AciFlowViewModel() {

    private val _deadline = MutableStateFlow<Deadline?>(null)
    val deadline: StateFlow<Deadline?> = _deadline

    fun getDeadlineById(deadlineId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            storageService.getDeadlineById(accountService.currentUserId, deadlineId)?.let { deadline ->
                _deadline.value = deadline
            }
        }
    }
}

class DeadlineDetailViewModelFactory(
    private val storageService: StorageService,
    private val accountService: AccountService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeadlineDetailViewModel::class.java)) {
            return DeadlineDetailViewModel(
                storageService = storageService,
                accountService = accountService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
