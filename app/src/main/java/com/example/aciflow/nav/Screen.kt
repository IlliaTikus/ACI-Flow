package com.example.aciflow.nav

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("register")
    data object HomeScreen : Screen("home")
    data object Profile : Screen("profile")
    data object ForumScreen : Screen("forum")
    data object Deadlines : Screen("deadlines")
    data object DeadlineDetail : Screen("deadline_detail/{deadlineTitle}") {
        fun createRoute(deadlineTitle: String) = "deadline_detail/$deadlineTitle"
    }

    data object EditDeadline : Screen("edit_deadline")
    data object Group : Screen("group")
    data object Post : Screen("post")
}