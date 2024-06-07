package com.example.aciflow.views.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.aciflow.widgets.EmailField
import com.example.aciflow.widgets.PasswordField
import com.example.aciflow.widgets.UsernameField
import com.example.aciflow.theme.AppTheme
import com.example.aciflow.views.register.RegisterUIState

@Composable
fun RegisterForm(
    registerState: RegisterUIState,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            UsernameField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = registerState.username,
                onValueChange = onUsernameChange,
                label = "Username",
                isError = registerState.errorState.usernameErrorState.hasError,
                errorText = registerState.errorState.usernameErrorState.errorMessage
            )
        }

        item {
            EmailField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = registerState.email,
                onValueChange = onEmailChange,
                label = "Email",
                isError = registerState.errorState.emailErrorState.hasError,
                errorText = registerState.errorState.emailErrorState.errorMessage
            )
        }

        item {
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = registerState.password,
                onValueChange = onPasswordChange,
                label = "Password",
                isError = registerState.errorState.passwordErrorState.hasError,
                errorText = registerState.errorState.passwordErrorState.errorMessage,
                imeAction = ImeAction.Done
            )
        }

        item {
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.paddingLarge),
                value = registerState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirm Password",
                isError = registerState.errorState.confirmPasswordErrorState.hasError,
                errorText = registerState.errorState.confirmPasswordErrorState.errorMessage,
                imeAction = ImeAction.Done
            )
        }

        item {
            Button(
                modifier = Modifier
                    .padding(top = AppTheme.dimens.paddingLarge)
                    .height(AppTheme.dimens.normalButtonHeight)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                onClick = onSubmit,
            ) {
                Text(text = "Create an account", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}