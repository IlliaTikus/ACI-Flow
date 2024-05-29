package com.example.aciflow

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.example.aciflow.nav.Screen

@Stable
class AppState(
    val navController: NavHostController
) {
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: Screen) {
        navController.navigate(route.route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: Screen, popUp: Screen){
        navController.navigate(route.route) {
            launchSingleTop = true
            popUpTo(popUp.route) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: Screen){
        navController.navigate(route.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

}