package com.example.aciflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aciflow.ui.components.SimpleTopAppBar
import com.example.aciflow.ui.components.TitleText
import com.example.aciflow.ui.components.login.UiEvent
import com.example.aciflow.ui.components.register.RegisterForm
import com.example.aciflow.ui.theme.AppTheme
import com.example.aciflow.ui.viewmodels.LoginViewModel

@Composable
fun RegisterScreen(loginViewModel: LoginViewModel = viewModel()) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .height(100.dp),
        topBar = {
            SimpleTopAppBar(title = "Register")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = AppTheme.dimens.paddingLarge)
                .padding(bottom = AppTheme.dimens.paddingExtraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleText(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                "New at ACI Flow?"
            )

            val registerState by remember {
                loginViewModel.registerState
            }

            RegisterForm(
                registerState = registerState,
                onEmailChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.RegisterEmailChanged(
                            inputValue = inputString
                        )
                    )
                },
                onPasswordChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.RegisterPasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                onConfirmPasswordChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.RegisterConfirmPasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                onUsernameChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.RegisterUsernameChanged(
                            inputValue = inputString
                        )
                    )
                },
                onSubmit = {
                    loginViewModel.onUiEvent(uiEvent = UiEvent.RegisterSubmit)
                }
            )
        }
    }
}