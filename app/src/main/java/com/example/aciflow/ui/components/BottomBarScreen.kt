package com.example.aciflow.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
//    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Profile: BottomBarScreen(
//        route = Screen.WatchlistScreen.route,
        title = "Profile",
        icon = Icons.Filled.Person
    )

    object Home: BottomBarScreen(
//        route = Screen.HomeScreen.route,
        title = "Home",
        icon = Icons.Filled.Forum
    )

    object Group: BottomBarScreen(
//        route = Screen.WatchlistScreen.route,
        title = "Group",
        icon = Icons.Filled.Groups
    )
}