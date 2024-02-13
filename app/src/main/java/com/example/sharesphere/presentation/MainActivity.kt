package com.example.sharesphere.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.presentation.navigation.App
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.ShareSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShareSphereTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainNavController = rememberNavController()
                    val navigator= Navigator(mainNavController)
                    App(mainNavController,navigator)
                }
            }
        }
    }
}

//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//@Composable
//fun App() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = ScreenSealedClass.AuthScreens.route
//    ) {
//        navigation(
//            startDestination = ScreenSealedClass.AuthScreens.UsernameScreen.route,
//            route = ScreenSealedClass.AuthScreens.route
//        ) {
//            composable(ScreenSealedClass.AuthScreens.SigninScreen.route) {
//                LoginScreen(navController = navController)
//            }
//            composable(ScreenSealedClass.AuthScreens.RegisterScreen.route) {
//                RegisterScreen(navController = navController)
//            }
//            composable(ScreenSealedClass.AuthScreens.UsernameScreen.route) {
//                UsernameScreen()
//            }
//        }
//        navigation(
//            startDestination = ScreenSealedClass.UserScreens.LandingScreen.route,
//            route = ScreenSealedClass.UserScreens.route
//        ) {
//            composable(ScreenSealedClass.UserScreens.LandingScreen.route) {
//                LandingScreen(navController = navController)
//            }
//        }
//
//    }
//}


