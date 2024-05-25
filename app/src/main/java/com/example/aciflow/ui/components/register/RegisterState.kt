package com.example.aciflow.ui.components.register

import com.example.aciflow.ui.components.login.ErrorState

data class RegisterState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false
)
data class RegistrationErrorState(
    val emailErrorState: ErrorState = ErrorState(),
    val usernameErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)

