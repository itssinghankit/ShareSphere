package com.example.sharesphere.presentation.navigation

sealed class NavigationActions{
    data class NavigateToSignin(val username:String?=null): NavigationActions()
    object NavigateBack:NavigationActions()
}