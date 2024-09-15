package com.example.sharesphere.presentation.navigation

sealed class ScreenSealedClass(val route:String) {

    object SplashScreen: ScreenSealedClass("splash_screen")

    object AuthScreens : ScreenSealedClass("auth"){
        object UsernameScreen : ScreenSealedClass("username_screen")
        object SigninScreen : ScreenSealedClass("signin_screen")
        object RegisterScreen : ScreenSealedClass("register_screen")

        object MobileScreen: ScreenSealedClass("mobile_screen")
        object VerifyOtpScreen: ScreenSealedClass("verifyotp_screen")
        object DetailsScreen: ScreenSealedClass("details_screen")
        object AvatarScreen: ScreenSealedClass("avatar_screen")
    }

    object UserScreens : ScreenSealedClass("user"){
        object UserScreen:ScreenSealedClass("user_screen")
        object HomeScreen: ScreenSealedClass("home_screen")
        object SearchScreen: ScreenSealedClass("search_screen")
        object PostScreen: ScreenSealedClass("post_screen")
        object NotificationScreen: ScreenSealedClass("notification_screen")
        object ProfileScreen: ScreenSealedClass("profile_screen")
        object AccountScreen: ScreenSealedClass("account_screen")
        object FFScreen:ScreenSealedClass("ff_screen")
        object ViewProfileScreen:ScreenSealedClass("view_profile_screen")
    }

    object ChatScreens :ScreenSealedClass("chat"){
        object ChatScreen:ScreenSealedClass("chat_screen")
    }

}