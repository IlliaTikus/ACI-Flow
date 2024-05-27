package com.example.aciflow.nav


sealed class Screens (val route: String){
    data object LoginScreen : Screens("login")
    data object RegisterScreen : Screens("register")
    data object HomeScreen : Screens("home")

    /*data object DetailScreen : Screen("detailscreen/{movieId}") {
        fun createRoute(movieId: Long) = "detailscreen/$movieId"
    }*/
}