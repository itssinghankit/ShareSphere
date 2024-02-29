package com.example.sharesphere.presentation.navigation

import androidx.navigation.NavController
import com.example.sharesphere.presentation.ScreenSealedClass

//Navigator class has ability to do all the navigation in the app
class Navigator(private val navController: NavController) {
    fun onAction(action: NavigationActions) {
        when (action) {
            is NavigationActions.NavigateBack -> {
                navController.popBackStack()
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToRegister -> {
                navController.navigate("${ScreenSealedClass.AuthScreens.RegisterScreen.route}/${action.username}")
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToSignin -> {
                navController.navigate(ScreenSealedClass.AuthScreens.SigninScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToUsername->{
                navController.navigate(ScreenSealedClass.AuthScreens.UsernameScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToMobile->{
                navController.navigate(ScreenSealedClass.AuthScreens.MobileScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens -> {
                navController.navigate(ScreenSealedClass.AuthScreens.route) {
                    //we don't want to see splash screen again
                    this.popUpTo(ScreenSealedClass.SplashScreen.route) {
                        inclusive = true
                    }
                }

            }

            is NavigationActions.NavigateToAuthScreens.NavigateToVerifyOtp->{
                navController.navigate(ScreenSealedClass.AuthScreens.VerifyOtpScreen.route)
            }

        }
    }
}