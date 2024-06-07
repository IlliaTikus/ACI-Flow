package com.example.aciflow.views.login

import com.example.aciflow.views.register.RegistrationErrorState

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val errorState: LoginErrorState = LoginErrorState(),
)

data class LoginErrorState(
    val emailErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState()
)

data class ErrorState(
    val hasError: Boolean = false,
    val errorMessage: String = ""
)
