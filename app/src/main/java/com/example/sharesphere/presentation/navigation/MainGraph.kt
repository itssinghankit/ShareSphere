package com.example.sharesphere.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.sharesphere.presentation.ScreenSealedClass
import com.example.sharesphere.presentation.screens.Splash.SplashScreen
import com.example.sharesphere.presentation.screens.authentication.mobile.MobileScreen
import com.example.sharesphere.presentation.screens.authentication.mobile.MobileViewModel
import com.example.sharesphere.presentation.screens.authentication.signin.SigninScreen
import com.example.sharesphere.presentation.screens.authentication.register.RegisterScreen
import com.example.sharesphere.presentation.screens.authentication.register.RegisterViewModel
import com.example.sharesphere.presentation.screens.authentication.username.UsernameScreen
import com.example.sharesphere.presentation.screens.authentication.username.UsernameViewModel
import com.example.sharesphere.presentation.ui.screens.home.LandingScreen
import com.example.sharesphere.util.composeAnimatedSlide

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun App(mainNavController: NavHostController,navigator: Navigator) {

    NavHost(
        navController = mainNavController,
        startDestination = ScreenSealedClass.AuthScreens.route
    ) {
        composeAnimatedSlide(route = ScreenSealedClass.SplashScreen.route) {
            SplashScreen(navigator)
        }
        navigation(
            startDestination = ScreenSealedClass.AuthScreens.SigninScreen.route,
            route = ScreenSealedClass.AuthScreens.route
        ) {

            composeAnimatedSlide(ScreenSealedClass.AuthScreens.SigninScreen.route) {
                SigninScreen(navController = mainNavController,navigator)
            }
            composeAnimatedSlide("${ScreenSealedClass.AuthScreens.RegisterScreen.route}/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    }
                )) {
                val viewModel:RegisterViewModel= hiltViewModel()
                RegisterScreen(viewModel,viewModel::onEvents,navigator)
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.UsernameScreen.route) {
                val viewmodel: UsernameViewModel = hiltViewModel()
                UsernameScreen(viewmodel, viewmodel::onEvent, navigator)
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.MobileScreen.route){
                val viewModel:MobileViewModel= hiltViewModel()
                MobileScreen(viewModel,viewModel::onEvent,navigator)
            }

        }
        navigation(
            startDestination = ScreenSealedClass.UserScreens.LandingScreen.route,
            route = ScreenSealedClass.UserScreens.route
        ) {
            composeAnimatedSlide(ScreenSealedClass.UserScreens.LandingScreen.route) {
                LandingScreen(navController = mainNavController)
            }
        }


    }
}