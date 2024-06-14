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
        if (accountService.hasUser)
            openAndPop(Screen.ForumScreen, Screen.LoginScreen)
    }

    fun onSubmit(openAndPop: (Screen, Screen) -> Unit) {
        var hasError = false

        if (!email.isValidEmail()) {
            uiState.value = uiState.value.copy(
                errorState = uiState.value.errorState.copy(
                    emailErrorState = ErrorState(hasError = true, errorMessage = "Invalid email")
                )
            )
            hasError = true
        }

        if (password.isBlank()) {
            uiState.value = uiState.value.copy(
                errorState = uiState.value.errorState.copy(
                    passwordErrorState = ErrorState(
                        hasError = true,
                        errorMessage = "Invalid password"
                    )
                )
            )
            hasError = true
        }

        if (hasError) {
            return
        }

        launchCatching {
            accountService.signIn(email, password)
            Log.i("DEBUG", "Signed in successfully :)")
            Log.i("DEBUG", "Userid: ${accountService.currentUserId}")
            openAndPop(Screen.ForumScreen, Screen.LoginScreen)
        }
    }

    fun onRegister(openAndPop: (Screen, Screen) -> Unit) {
        openAndPop(Screen.RegisterScreen, Screen.LoginScreen)
    }

}

class LoginViewModelFactory(private val accountService: AccountService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
