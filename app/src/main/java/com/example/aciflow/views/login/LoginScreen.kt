package com.example.aciflow.views.login

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
import com.example.aciflow.views.login.components.LoginForm
import com.example.aciflow.widgets.TitleText
import com.example.aciflow.theme.AppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aciflow.R
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen

@Composable
fun LoginScreen(navigate: (Screen) -> Unit, openAndPop: (Screen, Screen) -> Unit) {
    val factory = LoginViewModelFactory(accountService = AccountService.getAccountService())
    val viewModel: LoginViewModel = viewModel(factory = factory)
    viewModel.checkUserSignedIn(openAndPop)
    LoginScreenContent(viewModel = viewModel, navigate=navigate, openAndPop=openAndPop)
}

@Composable
fun LoginScreenContent(viewModel: LoginViewModel, navigate: (Screen) -> Unit, openAndPop: (Screen, Screen) -> Unit) {
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

            val uiState by remember {
                viewModel.uiState
            }

            LoginForm(
                loginState = uiState,
                onEmailOrMobileChange = { inputString ->
                    viewModel.onEmailChange(inputString)
                },
                onPasswordChange = { inputString ->
                    viewModel.onPasswordChange(inputString)
                },
                onSubmit = {
                    viewModel.onSubmit(openAndPop)
                },
                onRegister = {
                    viewModel.onRegister(navigate)
                })
        }
    }
}