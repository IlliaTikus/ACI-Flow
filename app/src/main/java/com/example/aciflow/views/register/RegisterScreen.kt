package com.example.aciflow.views.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(viewModel: RegisterViewModel, openAndPop: (Screen, Screen) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBack(openAndPop) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to login screen"
                        )
                    }
                }
            )
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
