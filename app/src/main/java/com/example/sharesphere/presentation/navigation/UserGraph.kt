package com.example.sharesphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.sharesphere.presentation.screens.user.home.HomeScreen
import com.example.sharesphere.presentation.screens.user.notification.NotificationScreen
import com.example.sharesphere.presentation.screens.user.post.PostScreen
import com.example.sharesphere.presentation.screens.user.profile.ProfileScreen
import com.example.sharesphere.presentation.screens.user.search.SearchScreen
import com.example.sharesphere.util.composeAnimatedSlide

@Composable
fun UserGraph(modifier: Modifier, navController: NavHostController, userNavigator: Navigator) {

    NavHost(
        modifier = modifier,
        navController = navController,
        route = ScreenSealedClass.UserScreens.route,
        startDestination = ScreenSealedClass.UserScreens.HomeScreen.route
    ) {
        composeAnimatedSlide(ScreenSealedClass.UserScreens.HomeScreen.route) {
            HomeScreen()
        }
        composeAnimatedSlide(ScreenSealedClass.UserScreens.SearchScreen.route) {
            SearchScreen()
        }
        composeAnimatedSlide(ScreenSealedClass.UserScreens.PostScreen.route) {
            PostScreen()
        }
        composeAnimatedSlide(ScreenSealedClass.UserScreens.NotificationScreen.route) {
            NotificationScreen()
        }
        composeAnimatedSlide(ScreenSealedClass.UserScreens.ProfileScreen.route) {
            ProfileScreen()
        }

    }

}