package com.example.sharesphere.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.presentation.ui.ScreenSealedClass
import com.example.sharesphere.presentation.screens.authentication.login.LoginScreen
import com.example.sharesphere.presentation.screens.authentication.register.RegisterScreen
import com.example.sharesphere.presentation.screens.authentication.username.UsernameScreen
import com.example.sharesphere.presentation.ui.screens.home.LandingScreen
import com.example.sharesphere.presentation.ui.theme.ShareSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShareSphereTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenSealedClass.UsernameScreen.route
    ) {
        composable(ScreenSealedClass.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(ScreenSealedClass.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(ScreenSealedClass.LandingScreen.route){
            LandingScreen(navController=navController)
        }
        composable(ScreenSealedClass.UsernameScreen.route){
            UsernameScreen()
        }
    }
}


