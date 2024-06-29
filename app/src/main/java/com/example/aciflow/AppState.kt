package com.example.aciflow

import androidx.compose.runtime.Stable
import androidx.compose.material.ScaffoldState
import androidx.navigation.NavHostController
import com.example.aciflow.common.snackbar.SnackbarManager
import com.example.aciflow.nav.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { message ->
                scaffoldState.snackbarHostState.showSnackbar(message)
                snackbarManager.clearSnackbarState()
            }
        }
    }

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