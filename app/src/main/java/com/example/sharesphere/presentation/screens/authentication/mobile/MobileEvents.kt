package com.example.sharesphere.presentation.screens.authentication.mobile

sealed class MobileEvents{
    data class MobileOnValueChange(val mobile:String):MobileEvents()
    object NextClicked:MobileEvents()
    object SnackBarShown:MobileEvents()
    object onNavigationDone:MobileEvents()
}
