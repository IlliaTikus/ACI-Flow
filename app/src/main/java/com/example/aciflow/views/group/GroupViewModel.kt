package com.example.aciflow.views.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.AciFlowViewModel
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first

class GroupViewModel(
    storageService: StorageService,
    accountService: AccountService
) : AciFlowViewModel() {
    private val _uiState = MutableStateFlow(emptyList<String>())
    val groupMembers: StateFlow<List<String>> = _uiState

    init {
        launchCatching {
            val department = accountService.currentUser.first().department!!
            storageService.getDepartmentMembers(department)
                .distinctUntilChanged().collect { userNames ->
                    _uiState.value = userNames
                }
        }
    }
}

class GroupViewModelFactory(
    private val storageService: StorageService,
    private val accountService: AccountService
): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroupViewModel::class.java)){
            return GroupViewModel(storageService = storageService, accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
