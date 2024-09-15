package com.example.sharesphere.presentation.navigation

sealed class NavigationActions {

    object NavigateToAuthScreens : NavigationActions() {

        object NavigateToSignIn : NavigationActions()
        object NavigateToUsername : NavigationActions()
        data class NavigateToRegister(val username: String? = null) : NavigationActions()
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
        data class NavigateToFFScreen(val userid:String,val followers:Boolean,val username:String):NavigationActions()
        object NavigateToAccountScreen:NavigationActions()
        data class NavigateToViewProfile(val userId:String):NavigationActions()
    }

    //from splash screens
    object NavigateToUserFromSplash:NavigationActions()
    object NavigateToMobileFromSplash:NavigationActions()
    object NavigateToDetailsFromSplash:NavigationActions()

    object NavigateToChatScreens:NavigationActions(){
    }


}