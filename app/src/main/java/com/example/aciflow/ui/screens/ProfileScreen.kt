package com.example.aciflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.ui.components.PasswordField
import com.example.aciflow.ui.components.SimpleBottomAppBar
import com.example.aciflow.ui.components.SimpleTopAppBar
import com.example.aciflow.ui.components.SmallTitleText
import com.example.aciflow.ui.components.TitleText
import com.example.aciflow.ui.components.UsernameField
import com.example.aciflow.ui.components.login.UiEvent
import com.example.aciflow.ui.theme.AppTheme
import com.example.aciflow.ui.viewmodels.LoginViewModel

@Composable
fun ProfileScreen(loginViewModel: LoginViewModel = viewModel(), navController: NavController) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .height(100.dp),
        topBar = {
                 SimpleTopAppBar(title ="My Profile")
        },
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = AppTheme.dimens.paddingLarge)
                .padding(bottom = AppTheme.dimens.paddingExtraLarge),
        ) {

            TitleText(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .align(Alignment.CenterHorizontally),
                "Change your data"
            )

            SmallTitleText(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .align(Alignment.CenterHorizontally),
                text = "michaelcampus@gmail.com"
            )

            val profileState by remember {
                loginViewModel.profileState
            }

            // Username
            UsernameField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = profileState.username.ifEmpty { "michael" },
                onValueChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.ProfileUsernameChanged(
                            inputValue = inputString
                        )
                    )
                },
                label = "Username",
                isError = profileState.errorState.usernameErrorState.hasError,
                errorText = profileState.errorState.usernameErrorState.errorMessage
            )

            // Password
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = profileState.password.ifEmpty { "12345678" },
                onValueChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.ProfilePasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                label = "Password",
                isError = profileState.errorState.passwordErrorState.hasError,
                errorText = profileState.errorState.passwordErrorState.errorMessage,
                imeAction = ImeAction.Done
            )

            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = profileState.password,
                onValueChange = { inputString ->
                    loginViewModel.onUiEvent(
                        uiEvent = UiEvent.ProfileConfirmPasswordChanged(
                            inputValue = inputString
                        )
                    )
                },
                label = "Confirm your new password",
                isError = profileState.errorState.passwordErrorState.hasError,
                errorText = profileState.errorState.passwordErrorState.errorMessage,
                imeAction = ImeAction.Done
            )

            Button(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .height(AppTheme.dimens.normalButtonHeight)
                    .requiredWidth(AppTheme.dimens.normalButtonWidth)
                    .align(Alignment.CenterHorizontally),
                onClick = { },
            ) {
                Text(text = "Change", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
