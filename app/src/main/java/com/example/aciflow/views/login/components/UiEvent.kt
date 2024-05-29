package com.example.aciflow.views.login.components

sealed class UiEvent {
    data class LoginEmailChanged(val inputValue: String) : UiEvent()
    data class LoginPasswordChanged(val inputValue: String) : UiEvent()
    data class RegisterUsernameChanged(val inputValue: String) : UiEvent()
    data class RegisterEmailChanged(val inputValue: String) : UiEvent()
    data class RegisterPasswordChanged(val inputValue: String) : UiEvent()
    data class RegisterConfirmPasswordChanged(val inputValue: String) : UiEvent()
    data class ProfileUsernameChanged(val inputValue: String) : UiEvent()
    data class ProfilePasswordChanged(val inputValue: String) : UiEvent()
    data class ProfileConfirmPasswordChanged(val inputValue: String) : UiEvent()
    data object LoginSubmit : UiEvent()
    data object RegisterSubmit : UiEvent()

    data object ProfileSubmit : UiEvent()
}