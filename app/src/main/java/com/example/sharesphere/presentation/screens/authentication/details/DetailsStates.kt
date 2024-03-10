package com.example.sharesphere.presentation.screens.authentication.details

import com.example.sharesphere.util.UiText

data class DetailsStates(
    val isLoading: Boolean=false,
    val navigate: Boolean = false,
    val errorMessage: UiText? = null,
    val isFullNameError:Boolean=false
)
