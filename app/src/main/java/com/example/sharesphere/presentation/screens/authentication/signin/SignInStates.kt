package com.example.sharesphere.presentation.screens.authentication.signin

import com.example.sharesphere.util.UiText

data class SignInStates(
    val isLoading: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val errorMessage:UiText?=null,
    val navigateToHomeScreen:Boolean=false,
    val navigateToForgetPasswordScreen:Boolean=false
)
