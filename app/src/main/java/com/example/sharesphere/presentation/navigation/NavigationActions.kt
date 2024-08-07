package com.example.sharesphere.presentation.navigation

sealed class NavigationActions {

    object NavigateToAuthScreens : NavigationActions() {
        data class NavigateToRegister(val username: String? = null) : NavigationActions()
        object NavigateToSignin : NavigationActions()
        object NavigateToUsername : NavigationActions()
        object NavigateToMobile : NavigationActions()
        object NavigateToVerifyOtp : NavigationActions()
        object NavigateToDetails : NavigationActions()
        data class NavigateToAvatar(
            val fullName: String? = null,
            val gender: String? = "male",
            val dob: Long? = null
        ) : NavigationActions()
    }

    object NavigateBack : NavigationActions()

    //for navigating to user screens like home
    object NavigateToUserScreens : NavigationActions() {
        object NavigateToHome : NavigationActions()
    }
}