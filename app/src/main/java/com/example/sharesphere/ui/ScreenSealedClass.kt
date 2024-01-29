package com.example.sharesphere.ui

sealed class ScreenSealedClass(val route:String) {
    object UsernameScreen : ScreenSealedClass("username_screen")
    object LoginScreen : ScreenSealedClass("login_screen")
    object RegisterScreen : ScreenSealedClass("register_screen")
    object LandingScreen : ScreenSealedClass("landing_screen")
}