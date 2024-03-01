package com.example.sharesphere.presentation.screens.authentication.username

sealed class UsernameEvents {
    object OnNextClick : UsernameEvents()
    data class OnUsernameValueChange(val username: String) : UsernameEvents()
    object SnackBarShown : UsernameEvents()
}
