package com.example.aciflow.views.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aciflow.R
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.widgets.SimpleTopAppBar
import com.example.aciflow.widgets.TitleText
import com.example.aciflow.views.register.components.RegisterForm
import com.example.aciflow.theme.AppTheme

@Composable
fun RegisterScreen(openAndPop: (Screen, Screen) -> Unit) {
    val factory = RegisterViewModelFactory(accountService = AccountService.getAccountService())
    val viewModel: RegisterViewModel = viewModel(factory = factory)
    viewModel.checkUserSignedIn(openAndPop)
    RegisterScreenContent(viewModel = viewModel, openAndPop = openAndPop)
}


@Composable
fun RegisterScreenContent(viewModel: RegisterViewModel, openAndPop: (Screen, Screen) -> Unit) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .height(100.dp),
        topBar = {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                    .size(50.dp)
                    .padding(AppTheme.dimens.paddingNormal)
                    .clickable {
                        viewModel.onBack(openAndPop)
                    })
            }
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

            val uiState by remember {
                viewModel.uiState
            }

            RegisterForm(
                registerState = uiState,
                onEmailChange = { inputString ->
                    viewModel.onEmailChange(inputString)
                },
                onUsernameChange = { inputString ->
                    viewModel.onUsernameChange(inputString)
                },
                onPasswordChange = { inputString ->
                    viewModel.onPasswordChange(inputString)
                },
                onConfirmPasswordChange = { inputString ->
                    viewModel.onConfirmPasswordChange(inputString)
                },
                onSubmit = {
                    viewModel.onSubmit(openAndPop)
                }
            )
        }
    }
}
