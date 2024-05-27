package com.example.aciflow

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class AppState(
    val navController: NavHostController
) {
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }
}