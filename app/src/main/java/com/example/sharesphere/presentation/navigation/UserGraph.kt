package com.example.sharesphere.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sharesphere.presentation.screens.chat.message.ChatMessageArguments
import com.example.sharesphere.presentation.screens.chat.message.ChatMessageScreen
import com.example.sharesphere.presentation.screens.chat.message.ChatMessageViewModel
import com.example.sharesphere.presentation.screens.user.home.HomeScreen
import com.example.sharesphere.presentation.screens.user.home.HomeViewModel
import com.example.sharesphere.presentation.screens.user.notification.NotificationScreen
import com.example.sharesphere.presentation.screens.user.post.PostScreen
import com.example.sharesphere.presentation.screens.user.post.PostViewModel
import com.example.sharesphere.presentation.screens.user.profile.ProfileScreen
import com.example.sharesphere.presentation.screens.user.profile.ProfileViewModel
import com.example.sharesphere.presentation.screens.user.search.SearchScreen
import com.example.sharesphere.presentation.screens.user.search.SearchViewModel
import com.example.sharesphere.util.composeAnimatedSlide
import timber.log.Timber

@Composable
fun UserGraph(
    modifier: Modifier, navController: NavHostController, userNavigator: Navigator,
    rootNavigator: Navigator
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        route = ScreenSealedClass.UserScreens.route,
        startDestination = ScreenSealedClass.UserScreens.HomeScreen.route
    ) {
        composable(ScreenSealedClass.UserScreens.HomeScreen.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToAccountScreen = { rootNavigator.onAction(NavigationActions.NavigateToUserScreens.NavigateToAccountScreen) },
                navigateToViewProfileScreen = { userId ->
                    rootNavigator.onAction(
                        NavigationActions.NavigateToUserScreens.NavigateToViewProfile(
                            userId = userId
                        )
                    )
                },
                navigateToChatScreens={rootNavigator.onAction(NavigationActions.NavigateToChatScreens)})
        }
        composable(ScreenSealedClass.UserScreens.SearchScreen.route) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(viewModel = viewModel, onEvent = viewModel::onEvent)
        }
        composable(ScreenSealedClass.UserScreens.PostScreen.route) {
            val viewModel: PostViewModel = hiltViewModel()
            PostScreen(viewModel = viewModel, onEvent = viewModel::onEvent)
        }
        composable(ScreenSealedClass.UserScreens.NotificationScreen.route) {
            NotificationScreen()
        }
        composable(ScreenSealedClass.UserScreens.ProfileScreen.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToFFScreen = { userid, followers, username ->
                    rootNavigator.onAction(
                        NavigationActions.NavigateToUserScreens.NavigateToFFScreen(
                            userid = userid,
                            followers = followers,
                            username = username
                        )
                    )
                },
                navigateToAccountScreen = { rootNavigator.onAction(NavigationActions.NavigateToUserScreens.NavigateToAccountScreen) }
            )
        }

    }

}
