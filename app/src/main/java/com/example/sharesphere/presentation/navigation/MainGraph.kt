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
import com.example.sharesphere.presentation.screens.Splash.SplashScreen
import com.example.sharesphere.presentation.screens.Splash.SplashViewModel
import com.example.sharesphere.presentation.screens.authentication.avatar.AvatarScreen
import com.example.sharesphere.presentation.screens.authentication.avatar.AvatarViewModel
import com.example.sharesphere.presentation.screens.authentication.details.DetailsScreen
import com.example.sharesphere.presentation.screens.authentication.details.DetailsViewModel
import com.example.sharesphere.presentation.screens.authentication.mobile.MobileScreen
import com.example.sharesphere.presentation.screens.authentication.mobile.MobileViewModel
import com.example.sharesphere.presentation.screens.authentication.register.RegisterScreen
import com.example.sharesphere.presentation.screens.authentication.register.RegisterViewModel
import com.example.sharesphere.presentation.screens.authentication.signin.SignInScreen
import com.example.sharesphere.presentation.screens.authentication.signin.SigninViewModel
import com.example.sharesphere.presentation.screens.authentication.username.UsernameScreen
import com.example.sharesphere.presentation.screens.authentication.username.UsernameViewModel
import com.example.sharesphere.presentation.screens.authentication.verifyOtp.VerifyOtpScreen
import com.example.sharesphere.presentation.screens.authentication.verifyOtp.VerifyOtpViewModel
import com.example.sharesphere.presentation.screens.chat.chat.ChatScreen
import com.example.sharesphere.presentation.screens.chat.chat.ChatViewModel
import com.example.sharesphere.presentation.screens.user.UserScreen
import com.example.sharesphere.presentation.screens.user.account.AccountScreen
import com.example.sharesphere.presentation.screens.user.followersFollowing.FFArguments
import com.example.sharesphere.presentation.screens.user.followersFollowing.FFViewModel
import com.example.sharesphere.presentation.screens.user.followersFollowing.FollowersFollowingScreen
import com.example.sharesphere.presentation.screens.user.viewProfile.ViewProfileArguments
import com.example.sharesphere.presentation.screens.user.viewProfile.ViewProfileScreen
import com.example.sharesphere.presentation.screens.user.viewProfile.ViewProfileViewModel
import com.example.sharesphere.util.composeAnimatedSlide

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RootNavGraph(rootNavController: NavHostController, navigator: Navigator) {

    NavHost(
        navController = rootNavController,
        startDestination = ScreenSealedClass.SplashScreen.route
    ) {

        //Testing///////////////////////////////////////////////////////////////////////////////////

        composeAnimatedSlide("${ScreenSealedClass.AuthScreens.AvatarScreen.route}/{fullName}/{dob}/{gender}",
            arguments = listOf(
                navArgument("fullName") {
                    type = NavType.StringType
                },
                navArgument("dob") {
                    type = NavType.LongType
                },
                navArgument("gender") {
                    type = NavType.StringType
                }

            )
        ) {
            val viewModel: AvatarViewModel = hiltViewModel()
            AvatarScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onBackClicked = { rootNavController.popBackStack() }) {
                navigator.onAction(NavigationActions.NavigateToUserScreens)
            }
        }

        //////////////// apart from nested navigation /////////////////

        composeAnimatedSlide(ScreenSealedClass.AuthScreens.DetailsScreen.route) {
            val viewModel: DetailsViewModel = hiltViewModel()
            DetailsScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onBackClick = { rootNavController.popBackStack() }) { fullName, dob, gender ->

                navigator.onAction(
                    NavigationActions.NavigateToAuthScreens.NavigateToAvatar(
                        fullName,
                        gender,
                        dob
                    )
                )

            }
        }

        composeAnimatedSlide(ScreenSealedClass.AuthScreens.MobileScreen.route) {
            val viewModel: MobileViewModel = hiltViewModel()
            MobileScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onBackClick = { rootNavController.popBackStack() }) {
                navigator.onAction(
                    NavigationActions.NavigateToAuthScreens.NavigateToVerifyOtp
                )
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        //Splash Screen
        composeAnimatedSlide(route = ScreenSealedClass.SplashScreen.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen(
                viewModel = viewModel,
                navigateToHomeScreen = { navigator.onAction(NavigationActions.NavigateToUserFromSplash) },
                navigateToSignInScreen = { navigator.onAction(NavigationActions.NavigateToAuthScreens) },
                navigateToMobileScreen = { navigator.onAction(NavigationActions.NavigateToMobileFromSplash) },
                navigateToDetailsScreen = { navigator.onAction(NavigationActions.NavigateToDetailsFromSplash) }
            )
        }

        //Auth Screens
        navigation(
            startDestination = ScreenSealedClass.AuthScreens.SigninScreen.route,
            route = ScreenSealedClass.AuthScreens.route
        ) {

            composeAnimatedSlide(ScreenSealedClass.AuthScreens.SigninScreen.route) {
                val viewModel: SigninViewModel = hiltViewModel()
                SignInScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackClick = { rootNavController.popBackStack() },
                    navigateToForgetPasswordScreen = {},
                    navigateToUsernameScreen = { navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToUsername) }) {
                    navigator.onAction(NavigationActions.NavigateToUserScreens)
                }
            }
            composeAnimatedSlide("${ScreenSealedClass.AuthScreens.RegisterScreen.route}/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    }
                )) {
                val viewModel: RegisterViewModel = hiltViewModel()
                RegisterScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvents,
                    onBackClick = { rootNavController.popBackStack() }) {
                    navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToMobile)
                }
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.UsernameScreen.route) {
                val viewmodel: UsernameViewModel = hiltViewModel()
                UsernameScreen(
                    viewModel = viewmodel,
                    onEvent = viewmodel::onEvent,
                    onBackClick = { rootNavController.popBackStack() }) {
                    navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToRegister(it))
                }
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.MobileScreen.route) {
                val viewModel: MobileViewModel = hiltViewModel()
                MobileScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackClick = { rootNavController.popBackStack() }) {
                    navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToVerifyOtp)
                }
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.VerifyOtpScreen.route) {
                val viewModel: VerifyOtpViewModel = hiltViewModel()
                VerifyOtpScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackClick = { rootNavController.popBackStack() }) {
                    navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToDetails)
                }
            }
            composeAnimatedSlide(ScreenSealedClass.AuthScreens.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackClick = { rootNavController.popBackStack() }) { fullName, dob, gender ->

                    navigator.onAction(
                        NavigationActions.NavigateToAuthScreens.NavigateToAvatar(
                            fullName,
                            gender,
                            dob
                        )
                    )

                }
            }
            composeAnimatedSlide("${ScreenSealedClass.AuthScreens.AvatarScreen.route}/{fullName}/{dob}/{gender}",
                arguments = listOf(
                    navArgument("fullName") {
                        type = NavType.StringType
                    },
                    navArgument("dob") {
                        type = NavType.LongType
                    },
                    navArgument("gender") {
                        type = NavType.StringType
                    }

                )
            ) {
                val viewModel: AvatarViewModel = hiltViewModel()
                AvatarScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackClicked = { rootNavController.popBackStack() }) {
                    navigator.onAction(NavigationActions.NavigateToUserScreens)
                }
            }

        }

        //User Screens
        navigation(
            startDestination = ScreenSealedClass.UserScreens.UserScreen.route,
            route = ScreenSealedClass.UserScreens.route
        ) {
            composeAnimatedSlide(
                route = ScreenSealedClass.UserScreens.UserScreen.route
            ) {
                UserScreen(
                    rootNavigator = navigator
                )
            }

            //other than bottom nav screens
            composeAnimatedSlide(
                route = "${ScreenSealedClass.UserScreens.FFScreen.route}/{${FFArguments.USER_ID.name}}/{${FFArguments.FOLLOWERS.name}}/{${FFArguments.USERNAME.name}}",
                arguments = listOf(
                    navArgument(FFArguments.USER_ID.name) {
                        type = NavType.StringType
                    },
                    navArgument(FFArguments.FOLLOWERS.name) {
                        type = NavType.BoolType
                    },
                    navArgument(FFArguments.USERNAME.name) {
                        type = NavType.StringType
                    }
                )
            ) {
                val viewModel: FFViewModel = hiltViewModel()
                FollowersFollowingScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackPressed = { rootNavController.popBackStack() })
            }

            composeAnimatedSlide(
                route = ScreenSealedClass.UserScreens.AccountScreen.route
            ) {
                AccountScreen()
            }

            composeAnimatedSlide(
                route = "${ScreenSealedClass.UserScreens.ViewProfileScreen.route}/{${ViewProfileArguments.USER_ID.name}}",
            ) {
                val viewModel: ViewProfileViewModel = hiltViewModel()
                ViewProfileScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvents,
                    onBackPressed = { rootNavController.popBackStack() }
                )
            }
        }

        navigation(
            startDestination = ScreenSealedClass.ChatScreens.ChatScreen.route,
            route = ScreenSealedClass.ChatScreens.route
        ) {
            composeAnimatedSlide(
                route = ScreenSealedClass.ChatScreens.ChatScreen.route
            ) {
                val viewModel: ChatViewModel = hiltViewModel()
                ChatScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::onEvent,
                    onBackPressed = { rootNavController.popBackStack() }
                )
            }
        }

    }
}