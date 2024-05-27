package com.example.aciflow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aciflow.nav.Screens
import com.example.aciflow.ui.theme.ACIFlowTheme
import com.example.aciflow.views.login.LoginScreen

@Composable
fun AciFlowApp() {
    ACIFlowTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Navigation(
                    modifier = Modifier.padding(innerPadding),
                    appState
                )
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    AppState(navController)
}

@Composable
fun Navigation(modifier: Modifier, appState: AppState) {
    NavHost(navController = appState.navController,
            modifier = modifier,
        startDestination = Screens.LoginScreen.route) {

        composable(route = Screens.LoginScreen.route) {
            LoginScreen()
        }

        composable(route = Screens.RegisterScreen.route){
            //HomeScreen(navController = navController)
        }

        composable(route = Screens.HomeScreen.route){
            //HomeScreen(navController = navController)
        }
    }
}