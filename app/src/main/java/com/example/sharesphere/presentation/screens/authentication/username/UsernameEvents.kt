package com.example.sharesphere.presentation.screens.authentication.username

sealed class UsernameEvents{
    object onNextClick: UsernameEvents()
    data class onValueChange(val username:String): UsernameEvents()
}
