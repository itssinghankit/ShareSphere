package com.example.sharesphere.presentation.navigation

import androidx.navigation.NavController
import com.example.sharesphere.presentation.ScreenSealedClass

//Navigator class has ability to do all the navigation in the app
class Navigator(private val navController:NavController) {
    fun onAction(action: NavigationActions){
        when(action){
            is NavigationActions.NavigateBack->{
                navController.popBackStack()
            }
            is NavigationActions.NavigateToSignin ->{
                navController.navigate("${ScreenSealedClass.AuthScreens.SigninScreen.route}/${action.username}")
            }

        }
    }
}