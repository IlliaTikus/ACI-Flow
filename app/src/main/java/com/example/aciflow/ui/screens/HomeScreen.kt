package com.example.aciflow.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aciflow.R
import com.example.aciflow.ui.components.SimpleBottomAppBar
import com.example.aciflow.ui.components.TitleText
import com.example.aciflow.ui.theme.AppTheme
import com.example.aciflow.views.login.LoginViewModel

@Composable
fun HomeScreen(loginViewModel: LoginViewModel = viewModel(), navController: NavController) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .height(100.dp),
        topBar = {
            Image(
                painter = painterResource(id = R.drawable.banner2),
                contentDescription = "ACI Flow Logo",
                modifier = Modifier
            )
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
                "Welcome to ACI Flow!"
            )
        }
    }
}
