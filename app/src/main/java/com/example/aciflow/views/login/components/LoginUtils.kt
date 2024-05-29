package com.example.aciflow.views.login.components

val emailEmptyErrorState = ErrorState(
    hasError = true,
    "Please enter your email"
)

val usernameEmptyErrorState = ErrorState(
    hasError = true,
    "Please enter your username"
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    "Please enter your password"
)

val confirmPasswordEmptyErrorState = ErrorState(
    hasError = true,
    "Please confirm your password"
)

val passwordMismatchErrorState = ErrorState(
    hasError = true,
    "Passwords do not match"
)