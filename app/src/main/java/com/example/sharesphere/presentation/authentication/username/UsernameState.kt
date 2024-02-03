package com.example.sharesphere.presentation.authentication.username

data class UsernameState(
    val isLoading: Boolean = false,
    val available: Boolean = false,
    val error: String = "",
    val username:String = "",

)
