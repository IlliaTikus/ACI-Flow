package com.example.aciflow.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.aciflow.ui.components.ProfileState
import com.example.aciflow.ui.components.login.ErrorState
import com.example.aciflow.ui.components.login.LoginErrorState
import com.example.aciflow.ui.components.login.LoginState
import com.example.aciflow.ui.components.login.UiEvent
import com.example.aciflow.ui.components.login.confirmPasswordEmptyErrorState
import com.example.aciflow.ui.components.login.emailEmptyErrorState
import com.example.aciflow.ui.components.login.passwordEmptyErrorState
import com.example.aciflow.ui.components.login.passwordMismatchErrorState
import com.example.aciflow.ui.components.login.usernameEmptyErrorState
import com.example.aciflow.ui.components.register.RegisterState

class LoginViewModel : ViewModel() {

    var loginState = mutableStateOf(LoginState())
        private set

    var registerState = mutableStateOf(RegisterState())
        private set

    var profileState = mutableStateOf(ProfileState())
        private set

    fun onUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {

            // Email changed
            is UiEvent.LoginEmailChanged -> {
                loginState.value = loginState.value.copy(
                    email = uiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            is UiEvent.RegisterEmailChanged -> {
                registerState.value = registerState.value.copy(
                    email = uiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        emailErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailEmptyErrorState
                    )
                )
            }

            // Username changed
            is UiEvent.RegisterUsernameChanged -> {
                registerState.value = registerState.value.copy(
                    username = uiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        usernameErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            usernameEmptyErrorState
                    )
                )
            }

            is UiEvent.ProfileUsernameChanged -> {
                profileState.value = profileState.value.copy(
                    username = uiEvent.inputValue,
                    errorState = profileState.value.errorState.copy(
                        usernameErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            usernameEmptyErrorState
                    )
                )
            }

            // Password changed
            is UiEvent.LoginPasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = uiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is UiEvent.RegisterPasswordChanged -> {
                registerState.value = registerState.value.copy(
                    password = uiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        passwordErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is UiEvent.ProfilePasswordChanged -> {
                profileState.value = profileState.value.copy(
                    password = uiEvent.inputValue,
                    errorState = profileState.value.errorState.copy(
                        passwordErrorState = if (uiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Confirm Password changed

            is UiEvent.RegisterConfirmPasswordChanged -> {
                registerState.value = registerState.value.copy(
                    confirmPassword = uiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            uiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            registerState.value.password.trim() != uiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }

            is UiEvent.ProfileConfirmPasswordChanged -> {
                registerState.value = registerState.value.copy(
                    confirmPassword = uiEvent.inputValue,
                    errorState = registerState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            uiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            registerState.value.password.trim() != uiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }

            // Submit Login
            is UiEvent.LoginSubmit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    // TODO Trigger login in authentication flow
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }
            }

            is UiEvent.RegisterSubmit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }
            }

            is UiEvent.ProfileSubmit -> {
                val inputsValidated = validateInputs()
                if (inputsValidated) {
                    loginState.value = loginState.value.copy(isLoginSuccessful = true)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailOrMobileString = loginState.value.email.trim()
        val passwordString = loginState.value.password
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

}