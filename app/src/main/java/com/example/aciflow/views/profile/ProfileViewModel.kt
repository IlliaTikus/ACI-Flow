package com.example.aciflow.views.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.views.login.LoginUIState
import com.example.aciflow.views.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileState(val name: String, val email: String)

class ProfileViewModel( private val accountService: AccountService) : ViewModel() {
    var uiState = mutableStateOf(ProfileUIState())
        private set

    fun onUsernameChanged(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }
}

class ProfileViewModelFactory(private val accountService: AccountService): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
