package com.example.sharesphere.presentation.screens.authentication.username

sealed class UsernameEvents{
    data class onNextClick(val username:String): UsernameEvents()
    data class onValueChange(val username:String): UsernameEvents()
    object snackbarShown:UsernameEvents()
}
