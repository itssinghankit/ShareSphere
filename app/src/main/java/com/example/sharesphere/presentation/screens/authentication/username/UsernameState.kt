package com.example.sharesphere.presentation.screens.authentication.username

import com.example.sharesphere.util.UiText

data class UsernameState(
    val isLoading: Boolean = false,
    val isAvailable: Boolean = false,
    val errorMessage: UiText? = null,
    val isUsernameError: Boolean = false,
    val textFieldErrorMessage: UiText? = null
)
