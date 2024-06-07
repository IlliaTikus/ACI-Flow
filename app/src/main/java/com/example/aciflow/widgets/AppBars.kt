package com.example.aciflow.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aciflow.AppState
import com.example.aciflow.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navigationIcons: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = navigationIcons
    )
}

@Composable
fun SimpleBottomAppBar(appState: AppState) {
    NavigationBar(
    ) {
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            icon = { Icon(Icons.Default.Forum, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == Screen.ForumScreen.route,
            onClick = { appState.navController.navigate(Screen.ForumScreen.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == Screen.Profile.route,
            onClick = { appState.navController.navigate(Screen.Profile.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Group, contentDescription = "Group") },
            label = { Text("Group") },
            selected = currentRoute == Screen.Group.route,
            onClick = { appState.navController.navigate(Screen.Group.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Event, contentDescription = "Deadlines") },
            label = { Text("Deadlines") },
            selected = currentRoute == Screen.Deadlines.route,
            onClick = { appState.navController.navigate(Screen.Deadlines.route) }
        )
    }
}