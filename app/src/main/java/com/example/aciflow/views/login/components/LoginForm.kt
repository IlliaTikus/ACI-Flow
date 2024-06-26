package com.example.aciflow.views.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.aciflow.widgets.EmailField
import com.example.aciflow.widgets.PasswordField
import com.example.aciflow.theme.AppTheme
import com.example.aciflow.views.login.LoginUIState

@Composable
fun LoginForm(
    loginState: LoginUIState,
    onEmailOrMobileChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        EmailField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = loginState.email,
            onValueChange = onEmailOrMobileChange,
            label = "Email",
            isError = loginState.errorState.emailErrorState.hasError,
            errorText = loginState.errorState.emailErrorState.errorMessage
        )

        // Password
        PasswordField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.paddingLarge),
            value = loginState.password,
            onValueChange = onPasswordChange,
            label = "Password",
            imeAction = ImeAction.Done,
            isError = loginState.errorState.passwordErrorState.hasError,
            errorText = loginState.errorState.passwordErrorState.errorMessage
        )

        Button(
            modifier = Modifier
                .padding(top = AppTheme.dimens.paddingLarge)
                .height(AppTheme.dimens.normalButtonHeight)
                .requiredWidth(AppTheme.dimens.minButtonWidth)
                .align(Alignment.CenterHorizontally)
            , onClick = onSubmit,
        ) {
            Text(text = "Sign In", style = MaterialTheme.typography.titleMedium)
        }

        Row(
            modifier = Modifier.padding(top = AppTheme.dimens.paddingLarge),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Don't have an account?")

            Text(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.paddingExtraSmall)
                    .clickable { onRegister() }, text = "Register", color = MaterialTheme.colorScheme.primary
            )
        }
    }
}