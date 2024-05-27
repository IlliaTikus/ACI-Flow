package com.example.aciflow.views.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.aciflow.common.ext.isValidEmail

class LoginViewModel : ViewModel() {

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

    fun onSubmit() {
        if(!email.isValidEmail()) {
            Log.e("DEBUG", "invalid email")
            return
        }

        if(password.isBlank()) {
            Log.e("DEBUG", "empty password")
            return
        }

        //accountService authenticate
    }

    /*private fun validateInputs(): Boolean {
        val emailOrMobileString = uiState.value.email.trim()
        val passwordString = uiState.value.password
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                uiState.value = uiState.value.copy(
                    errorState = LoginErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                uiState.value = uiState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                uiState.value = uiState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }*/

}