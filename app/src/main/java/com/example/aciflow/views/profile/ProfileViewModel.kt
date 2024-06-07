package com.example.aciflow.views.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.AppState
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.views.AciFlowViewModel
import com.example.aciflow.views.login.LoginUIState
import com.example.aciflow.views.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel( private val accountService: AccountService, private val appState: AppState) : AciFlowViewModel() {
    var uiState = mutableStateOf(ProfileUIState())
        private set

    fun onUsernameChanged(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onLogout() {
        launchCatching {
            accountService.signOut()
            appState.clearAndNavigate(Screen.LoginScreen)
        }
    }
}

class ProfileViewModelFactory(private val accountService: AccountService, private val appState: AppState): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(accountService = accountService, appState = appState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
