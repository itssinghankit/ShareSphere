package com.example.sharesphere.presentation

sealed class ScreenSealedClass(val route:String) {

    object SplashScreen:ScreenSealedClass("splash_screen")

    object AuthScreens : ScreenSealedClass("auth"){
        object UsernameScreen : ScreenSealedClass("username_screen")
        object SigninScreen : ScreenSealedClass("signin_screen")
        object RegisterScreen : ScreenSealedClass("register_screen")

        object MobileScreen: ScreenSealedClass("mobile_screen")
        object VerifyOtpScreen:ScreenSealedClass("verifyotp_screen")
        object DetailsScreen:ScreenSealedClass("details_screen")
        object AvatarScreen:ScreenSealedClass("avatar_screen")
    }

    object UserScreens : ScreenSealedClass("user"){
        object LandingScreen : ScreenSealedClass("landing_screen")
        object HomeScreen:ScreenSealedClass("home_screen")
    }

}