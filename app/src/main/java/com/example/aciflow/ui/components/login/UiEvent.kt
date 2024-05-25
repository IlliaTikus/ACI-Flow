package com.example.aciflow.ui.components.login
/**
 * Login Screen Events
 */
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
    object LoginSubmit : UiEvent()
    object RegisterSubmit : UiEvent()

    object ProfileSubmit : UiEvent()
}