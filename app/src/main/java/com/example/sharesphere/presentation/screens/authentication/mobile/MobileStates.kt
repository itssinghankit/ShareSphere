package com.example.sharesphere.presentation.screens.authentication.mobile

import com.example.sharesphere.util.UiText

data class MobileStates(
    val isLoading: Boolean = false,
    val isMobileError: Boolean = false,
    val errorMessage: UiText? = null,
    val navigate:Boolean=false
)
