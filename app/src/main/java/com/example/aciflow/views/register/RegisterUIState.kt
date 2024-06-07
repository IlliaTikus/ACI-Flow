package com.example.aciflow.views.register

data class RegisterUIState(
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

data class ErrorState(
    val hasError: Boolean = false,
    val errorMessage: String = ""
)
