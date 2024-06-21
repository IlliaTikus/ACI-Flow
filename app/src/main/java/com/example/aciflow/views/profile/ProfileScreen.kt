package com.example.aciflow.views.profile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.AppState
import com.example.aciflow.model.services.AccountService
import com.example.aciflow.nav.Screen
import com.example.aciflow.theme.AppTheme
import com.example.aciflow.views.login.LoginScreenContent
import com.example.aciflow.views.login.LoginViewModel
import com.example.aciflow.views.login.LoginViewModelFactory
import com.example.aciflow.widgets.SmallTitleText
import com.example.aciflow.widgets.SmallTitleTextWhite
import com.example.aciflow.widgets.TitleText
import com.example.aciflow.widgets.UsernameField

// TODO: appState hier bitte rausnehmen, mehr Info siehe ViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, appState: AppState) {
//    val profileState by profileViewModel.profileState.collectAsState()
    val factory = ProfileViewModelFactory(accountService = AccountService.getAccountService(), appState = appState)
    val viewModel: ProfileViewModel = viewModel(factory = factory)

    fun onUsernameChanged(inputString: String) {
        viewModel.onUsernameChanged(inputString)
    }

    fun updateUsername() {
        viewModel.updateUsername()
    }

    fun onLogout () {
        viewModel.onLogout()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
//                .padding(horizontal = AppTheme.dimens.paddingLarge)
//                .padding(bottom = AppTheme.dimens.paddingExtraLarge),
        ) {
//            TitleText(
//                modifier = Modifier
//                    .padding(top = AppTheme.dimens.paddingLarge)
//                    .align(Alignment.CenterHorizontally),
//                "Change your data"
//            )

            val uiState by viewModel.uiState.collectAsState()

            SmallTitleText(
                text = uiState.email.orEmpty(),
            )

            SmallTitleTextWhite(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingSmall),
                text = "Course: ${uiState.course}"
            )

            SmallTitleTextWhite(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingSmall),
                text = "Semester: ${uiState.semester}"
            )

//            Text(text = "Name: ${uiState.course}", style = MaterialTheme.typography.titleSmall)
//            Text(text = "Email: ${uiState.semester}", style = MaterialTheme.typography.bodySmall)

            SmallTitleText(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingExtraLarge),
                text = "Change your data"
            )

            // Username
            UsernameField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingSmall),
                value = uiState.username.orEmpty(),
                onValueChange = { newValue -> onUsernameChanged(newValue) },
                label = "Username"
            )

            Row(
                modifier = Modifier.padding(top = AppTheme.dimens.paddingSmall),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Forgot Password?")

                Text(
                    modifier = Modifier
                        .padding(start = AppTheme.dimens.paddingExtraSmall)
                        .clickable { }, text = "Reset password", color = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .height(AppTheme.dimens.normalButtonHeight)
                    .requiredWidth(AppTheme.dimens.normalButtonWidth)
                    .align(Alignment.CenterHorizontally),
                onClick = {updateUsername()},
            ) {
                Text(text = "Change", style = MaterialTheme.typography.titleMedium)
            }

            Button(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .height(AppTheme.dimens.normalButtonHeight)
                    .requiredWidth(AppTheme.dimens.normalButtonWidth)
                    .align(Alignment.CenterHorizontally),
                onClick = { onLogout() },
            ) {
                Text(text = "Logout", style = MaterialTheme.typography.titleMedium)
            }
        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//            Text(text = "Name: ${profileState.name}", style = MaterialTheme.typography.titleSmall)
//            Text(text = "Email: ${profileState.email}", style = MaterialTheme.typography.bodySmall)
//        }
    }
}

/*

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
import com.example.aciflow.widgets.PasswordField
import com.example.aciflow.ui.components.SimpleBottomAppBar
import com.example.aciflow.ui.components.SimpleTopAppBar
import com.example.aciflow.ui.components.SmallTitleText
import com.example.aciflow.ui.components.TitleText
import com.example.aciflow.widgets.UsernameField
import com.example.aciflow.ui.components.login.UiEvent
import com.example.aciflow.ui.theme.AppTheme
import com.example.aciflow.views.login.LoginViewModel

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
*/
