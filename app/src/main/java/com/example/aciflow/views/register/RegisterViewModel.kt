package com.example.aciflow.views.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aciflow.common.ext.isValidEmail
import com.example.aciflow.common.ext.isValidPassword
import com.example.aciflow.common.ext.passwordMatches
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.views.AciFlowViewModel
import com.example.aciflow.views.login.components.usernameEmptyErrorState

class RegisterViewModel(
    private val accountService: AccountService
) : AciFlowViewModel() {

    var uiState = mutableStateOf(RegisterUIState())
        private set

    private val email
        get() = uiState.value.email
    private val username
        get() = uiState.value.username
    private val password
        get() = uiState.value.password
    private val confirmPassword
        get() = uiState.value.confirmPassword

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun checkUserSignedIn(openAndPop: (Screen, Screen) -> Unit) {
        if (accountService.hasUser)
            openAndPop(Screen.HomeScreen, Screen.LoginScreen)
    }

    fun onBack(openAndPop: (Screen, Screen) -> Unit) {
        openAndPop(Screen.LoginScreen, Screen.RegisterScreen)
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

        if (username.isBlank()) {
            uiState.value = uiState.value.copy(
                errorState = uiState.value.errorState.copy(
                    usernameErrorState = ErrorState(
                        hasError = true,
                        errorMessage = "Username cannot be empty"
                    )
                )
            )
            hasError = true
        }

        if (!password.isValidPassword()) {
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

        if (!confirmPassword.passwordMatches(password)) {
            uiState.value = uiState.value.copy(
                errorState = uiState.value.errorState.copy(
                    confirmPasswordErrorState = ErrorState(
                        hasError = true,
                        errorMessage = "Passwords do not match"
                    )
                )
            )
            hasError = true
        }

        if (hasError) {
            return
        }

        launchCatching {
            accountService.createUser(username, email, password)
            Log.i("VM", "Signed in successfully :)")
            Log.i("VM", "Userid: ${accountService.currentUserId}")
            openAndPop(Screen.LoginScreen, Screen.RegisterScreen)
        }
    }

}

class RegisterViewModelFactory(private val accountService: AccountService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(accountService = accountService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
