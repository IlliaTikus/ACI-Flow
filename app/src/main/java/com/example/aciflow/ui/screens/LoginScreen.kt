package com.example.aciflow.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.aciflow.ui.components.login.LoginForm
import com.example.aciflow.ui.components.TitleText
import com.example.aciflow.ui.theme.AppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aciflow.R
import com.example.aciflow.ui.components.login.UiEvent
import com.example.aciflow.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .height(100.dp),
        topBar = {
            Image(
                painter = painterResource(id = R.drawable.banner2),
                contentDescription = "ACI Flow Logo",
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = AppTheme.dimens.paddingLarge)
                .padding(bottom = AppTheme.dimens.paddingExtraLarge)
        ) {

            TitleText(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
                "Login to ACI Flow"
            )

            val loginState by remember {
                loginViewModel.loginState
            }

            LoginForm(
                loginState = loginState,
                onEmailOrMobileChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.LoginEmailChanged(
                            inputString
                        )
                    )
                },
                onPasswordChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.LoginPasswordChanged(
                            inputString
                        )
                    )
                },
                onSubmit = {
                    loginViewModel.onUiEvent(uiEvent = UiEvent.LoginSubmit)
                })
        }
    }
}