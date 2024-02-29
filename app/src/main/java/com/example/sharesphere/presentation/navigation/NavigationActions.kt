package com.example.sharesphere.presentation.navigation

sealed class NavigationActions{

    object NavigateToAuthScreens:NavigationActions(){
        data class NavigateToRegister(val username:String?=null): NavigationActions()
        object NavigateToSignin:NavigationActions()
        object NavigateToUsername:NavigationActions()
        object NavigateToMobile:NavigationActions()
        object NavigateToVerifyOtp:NavigationActions()
    }

    object NavigateBack:NavigationActions()
}