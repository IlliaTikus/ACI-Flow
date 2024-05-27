package com.example.aciflow.ui.components

import com.example.aciflow.ui.components.login.ErrorState

data class ProfileState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false
)
data class RegistrationErrorState(
    val usernameErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState(),
    val confirmPasswordErrorState: ErrorState = ErrorState()
)

