package com.example.sharesphere.presentation.screens.authentication.details

import com.example.sharesphere.presentation.screens.authentication.mobile.MobileEvents

sealed class DetailsEvents {
    data class OnFullNameValueChange(val fullName:String): DetailsEvents()
    object OnNextClicked: DetailsEvents()
    object OnSnackBarShown: DetailsEvents()
    object OnNavigationDone: DetailsEvents()
}