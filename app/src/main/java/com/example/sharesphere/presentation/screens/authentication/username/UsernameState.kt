package com.example.sharesphere.presentation.screens.authentication.username

import com.example.sharesphere.util.UiText

data class UsernameState(
    val isLoading: Boolean = false,
    val available: Boolean = false,
    val error: UiText? = null,
    val isError:Boolean = false
)
