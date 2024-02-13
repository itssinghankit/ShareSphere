package com.example.sharesphere.presentation.screens.authentication.username

import com.example.sharesphere.util.UiText

data class UsernameState(
    val isLoading: Boolean = false,
    val isavailable: Boolean = false,
    val isError:Boolean = false,
    val errorMessage: UiText? = null,
    val isTextfieldError:Boolean=false,
    val textfieldErrorMessage:UiText?=null,
    val showSnackBar:Boolean=false
)
