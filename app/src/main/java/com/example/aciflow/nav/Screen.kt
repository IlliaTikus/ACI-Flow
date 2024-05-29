package com.example.aciflow.nav


sealed class Screen (val route: String){
    data object LoginScreen : Screen("login")
    data object RegisterScreen : Screen("register")
    data object HomeScreen : Screen("home")

    /*data object DetailScreen : Screen("detailscreen/{movieId}") {
        fun createRoute(movieId: Long) = "detailscreen/$movieId"
    }*/
}