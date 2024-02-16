package com.example.sharesphere.presentation.screens.authentication.register

import com.example.sharesphere.util.UiText

data class RegisterStates(
    val isLoading: Boolean?= false,
    val textFieldError: UiText? = null,
    val isEmailError:Boolean=false,
    val isPasswordError:Boolean=false,
    val isCPasswordError:Boolean=false
)