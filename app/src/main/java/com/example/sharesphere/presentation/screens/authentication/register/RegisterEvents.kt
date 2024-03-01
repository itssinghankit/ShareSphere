package com.example.sharesphere.presentation.screens.authentication.register

sealed class RegisterEvents{
    data class OnEmailValueChange(val email:String):RegisterEvents()
    data class OnPasswordValueChange(val password:String):RegisterEvents()
    data class OnCPasswordValueChange(val cPassword:String):RegisterEvents()
    object onNextClick:RegisterEvents()
    object onSnackBarShown:RegisterEvents()
    object onNavigationDone:RegisterEvents()
}
