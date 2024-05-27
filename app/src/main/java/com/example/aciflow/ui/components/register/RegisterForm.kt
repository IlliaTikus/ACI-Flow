package com.example.aciflow.ui.components.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.aciflow.ui.components.EmailField
import com.example.aciflow.ui.components.PasswordField
import com.example.aciflow.ui.components.UsernameField
import com.example.aciflow.ui.theme.AppTheme

@Composable
fun RegisterForm(
    registerState: RegisterState,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
) {

    // Login Inputs Section
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

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

        // Password
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

        PasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = registerState.password,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm Password",
            isError = registerState.errorState.confirmPasswordErrorState.hasError,
            errorText = registerState.errorState.confirmPasswordErrorState.errorMessage,
            imeAction = ImeAction.Done
        )


        Button(
            modifier = Modifier
                .padding(top = AppTheme.dimens.paddingLarge)
                .height(AppTheme.dimens.normalButtonHeight)
                .requiredWidth(AppTheme.dimens.normalButtonWidth)
                .align(Alignment.CenterHorizontally)
            , onClick = onSubmit,
        ) {
            Text(text = "Create an account", style = MaterialTheme.typography.titleMedium)
        }
    }
}