package com.example.aciflow.views.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.common.ext.isValidEmail
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.views.AciFlowViewModel

class LoginViewModel(
    private val accountService: AccountService
) : AciFlowViewModel() {

    var uiState = mutableStateOf(LoginUIState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun checkUserSignedIn(openAndPop: (Screen, Screen) -> Unit) {
        if(accountService.hasUser)
            openAndPop(Screen.HomeScreen, Screen.LoginScreen)
    }

    fun onSubmit(openAndPop: (Screen, Screen) -> Unit) {
        if(!email.isValidEmail()) {
            Log.e("DEBUG", "invalid email")
            return
        }

        if(password.isBlank()) {
            Log.e("DEBUG", "empty password")
            return
        }

        launchCatching {
            accountService.signIn(email, password)
            Log.i("VM", "Signed in successfully :)")
            Log.i("VM", "Userid: ${accountService.currentUserId}")
            openAndPop(Screen.HomeScreen, Screen.LoginScreen)
        }
    }

}

class LoginViewModelFactory(private val accountService: AccountService): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
