package com.example.sharesphere.presentation.navigation

import android.net.Uri
import androidx.navigation.NavController
import timber.log.Timber

//Navigator class has ability to do all the navigation in the app
class Navigator(private val navController: NavController) {
    fun onAction(action: NavigationActions) {
        when (action) {

            //Back Button
            is NavigationActions.NavigateBack -> {
                navController.popBackStack()
            }

            //splash screen
            NavigationActions.NavigateToDetailsFromSplash -> {
                navController.navigate(ScreenSealedClass.AuthScreens.DetailsScreen.route) {
                    this.popUpTo(ScreenSealedClass.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            NavigationActions.NavigateToMobileFromSplash -> {
                navController.navigate(ScreenSealedClass.AuthScreens.MobileScreen.route) {
                    this.popUpTo(ScreenSealedClass.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            NavigationActions.NavigateToUserFromSplash -> {
                navController.navigate(ScreenSealedClass.UserScreens.route) {
                    this.popUpTo(ScreenSealedClass.SplashScreen.route) {
                        inclusive = true
                    }
                }
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

            is NavigationActions.NavigateToAuthScreens.NavigateToSignIn -> {
                navController.navigate(ScreenSealedClass.AuthScreens.SigninScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToUsername -> {
                navController.navigate(ScreenSealedClass.AuthScreens.UsernameScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToMobile -> {
                navController.navigate(ScreenSealedClass.AuthScreens.MobileScreen.route) {
                    //TODO: nested navigation popup problem
                    this.popUpTo(ScreenSealedClass.AuthScreens.RegisterScreen.route) {
                        inclusive = true
                    }
                }
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToVerifyOtp -> {
                navController.navigate(ScreenSealedClass.AuthScreens.VerifyOtpScreen.route)
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToDetails -> {
                navController.navigate(ScreenSealedClass.AuthScreens.DetailsScreen.route) {
                    this.popUpTo(ScreenSealedClass.AuthScreens.VerifyOtpScreen.route) {
                        inclusive = true
                    }
                }
            }

            is NavigationActions.NavigateToAuthScreens.NavigateToAvatar -> {
                navController.navigate("${ScreenSealedClass.AuthScreens.AvatarScreen.route}/${action.fullName}/${action.dob}/${action.gender}")
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

            is NavigationActions.NavigateToUserScreens.NavigateToFFScreen -> {
                navController.navigate("${ScreenSealedClass.UserScreens.FFScreen.route}/${action.userid}/${action.followers}/${action.username}")
            }

            NavigationActions.NavigateToUserScreens.NavigateToAccountScreen -> {
                navController.navigate(ScreenSealedClass.UserScreens.AccountScreen.route)
            }

            is NavigationActions.NavigateToUserScreens.NavigateToViewProfile -> {
                navController.navigate("${ScreenSealedClass.UserScreens.ViewProfileScreen.route}/${action.userId}")
            }

            NavigationActions.NavigateToChatScreens -> {
                navController.navigate(ScreenSealedClass.ChatScreens.route)
            }

            is NavigationActions.NavigateToChatScreens.NavigateToChatMessageScreen -> {
                navController.navigate("${ScreenSealedClass.ChatScreens.ChatMessageScreen.route}/${action.chatId}/${action.fullName}/${action.username}/${Uri.encode(action.avatar)}")
            }
        }
    }
}