package com.example.sharesphere.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.presentation.navigation.Navigator
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class NavigationModule {

//    fun providesNavController():NavController{
//        return NavHostController()
//    }
//
//
//    fun providesNavigator():Navigator{}

}