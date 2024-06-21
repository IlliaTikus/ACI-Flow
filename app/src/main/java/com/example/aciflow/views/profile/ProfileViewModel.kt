package com.example.aciflow.views.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aciflow.AppState
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.views.AciFlowViewModel
import com.example.aciflow.views.login.LoginUIState
import com.example.aciflow.views.login.LoginViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

// TODO: appState hier bitte rausnehmen, stattdessen callback für clearandnavigate
// bei onLogout übergeben
class ProfileViewModel( private val accountService: AccountService, private val appState: AppState) : AciFlowViewModel() {
    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState.asStateFlow()

    init {
        launchCatching {
            _uiState.value = _uiState.value.copy(email = accountService.currentUserEmail)
            accountService.currentUser.distinctUntilChanged().collect { user ->
                // TODO: properly handle errors
                _uiState.value = _uiState.value.copy(
                    username = user.username,
                    course = accountService.getDepartmentName(user.department!!),   // xD
                    semester = user.semester
                )
            }
        }
    }

    fun onUsernameChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(username = newValue)
    }

    fun updateUsername() {
        viewModelScope.launch {
            _uiState.value.username?.let { onChange(it) }
        }
    }

    private suspend fun onChange(username: String) {
        accountService.updateUserName(username)
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
