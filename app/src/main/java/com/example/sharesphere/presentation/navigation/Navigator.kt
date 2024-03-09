package com.example.sharesphere.presentation.navigation

import androidx.navigation.NavController
import com.example.sharesphere.presentation.ScreenSealedClass

//Navigator class has ability to do all the navigation in the app
class Navigator(private val navController: NavController) {
    fun onAction(action: NavigationActions) {
        when (action) {

            //Back Button

            is NavigationActions.NavigateBack -> {
                navController.popBackStack()
            }

            //Auth Screens

            is NavigationActions.NavigateToAuthScreens -> {
                navController.navigate(ScreenSealedClass.AuthScreens.route) {
                    //we don't want to see splash screen again
                    this.popUpTo(ScreenSealedClass.SplashScreen.route) {
                        inclusive = true
                    }
                }

            }

            is NavigationActions.NavigateToAuthScreens.NavigateToRegister -> {
                navController.navigate("${ScreenSealedClass.AuthScreens.RegisterScreen.route}/${action.username}")
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToSignin -> {
                navController.navigate(ScreenSealedClass.AuthScreens.SigninScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToUsername -> {
                navController.navigate(ScreenSealedClass.AuthScreens.UsernameScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToMobile -> {
                navController.navigate(ScreenSealedClass.AuthScreens.MobileScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToVerifyOtp -> {
                navController.navigate(ScreenSealedClass.AuthScreens.VerifyOtpScreen.route)
            }


            //User Screens

            is NavigationActions.NavigateToUserScreens -> {
                navController.navigate(ScreenSealedClass.UserScreens.route) {
                    this.popUpTo(ScreenSealedClass.AuthScreens.route) {
                        inclusive = true
                    }
                }
            }

            is NavigationActions.NavigateToUserScreens.NavigateToHome -> {
                navController.navigate(ScreenSealedClass.UserScreens.HomeScreen.route)
            }

        }
    }
}