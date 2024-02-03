package com.example.sharesphere.presentation.authentication.username

sealed class UsernameEvents{
    object onNextClick:UsernameEvents()
    data class onValueChange(val username:String):UsernameEvents()
}
