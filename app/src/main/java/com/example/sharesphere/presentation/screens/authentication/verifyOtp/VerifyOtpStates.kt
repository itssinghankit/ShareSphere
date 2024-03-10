package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import com.example.sharesphere.util.UiText

data class VerifyOtpStates(
    val isLoading: Boolean = false,
    val isInputsValidated: Boolean = false,
    val errorMessage:UiText?=null,
    val navigate:Boolean=false
)