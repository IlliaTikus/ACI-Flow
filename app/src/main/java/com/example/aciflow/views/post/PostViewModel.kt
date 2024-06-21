package com.example.aciflow.views.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.AppState
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.model.services.StorageService
import com.example.aciflow.views.AciFlowViewModel
import com.example.aciflow.views.forum.HomeUIState
import com.example.aciflow.views.profile.ProfileUIState
import com.example.aciflow.views.profile.ProfileViewModel
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PostViewModel(
    private val accountService: AccountService,
    private val storageService: StorageService,
    appState: AppState
) : AciFlowViewModel() {
    private val _uiState = MutableStateFlow(PostUIState())
    val uiState: StateFlow<PostUIState> = _uiState.asStateFlow()

    fun updateDescription(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun publishPost() {
        CoroutineScope(Dispatchers.IO).launch {
            val department = accountService.currentUser.first().department!!
            _uiState.value = _uiState.value.copy(
                departmentName = accountService.getDepartmentName(department)
            )
            storageService.addPost(department, accountService.currentUserId, _uiState.value.description)
        }
    }
}

class PostViewModelFactory(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val appState: AppState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(accountService = accountService, storageService = storageService, appState = appState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
