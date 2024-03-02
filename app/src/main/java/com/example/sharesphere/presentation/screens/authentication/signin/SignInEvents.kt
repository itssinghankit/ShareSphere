package com.example.sharesphere.presentation.screens.authentication.signin

sealed class SignInEvents {

    object OnNextClick:SignInEvents()
    object OnSnackBarShown:SignInEvents()
    data class OnEmailValueChange(val email:String):SignInEvents()
    data class OnPasswordValueChange(val password:String):SignInEvents()
    object OnNavigationDone:SignInEvents()
    object OnForgetPasswordClicked:SignInEvents()
}