package com.example.aciflow.views.deadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.model.Deadline
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.AciFlowViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class DeadlinesViewModel(
    storageService: StorageService,
    accountService: AccountService
) : AciFlowViewModel() {
    private val _uiState = MutableStateFlow(emptyList<Deadline>())
    val deadlines: StateFlow<List<Deadline>> = _uiState

    init {
        launchCatching {
            storageService.getDeadlinesForUser(accountService.currentUserId).distinctUntilChanged()
                .collect { deadlines ->
                    _uiState.value = deadlines
                }
        }
    }
}


class DeadlinesViewModelFactory(
    private val storageService: StorageService,
    private val accountService: AccountService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeadlinesViewModel::class.java)) {
            return DeadlinesViewModel(
                storageService = storageService,
                accountService = accountService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
