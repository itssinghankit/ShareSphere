package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import com.example.sharesphere.util.UiText

data class VerifyOtpStates(
    val isLoading: Boolean = false,
    val showSnackBar: Boolean = false,
    val isInputsValidated: Boolean = false,
    val isError:Boolean?=null,
    val errorMessage:UiText?=UiText.DynamicString("Something went wrong")
)