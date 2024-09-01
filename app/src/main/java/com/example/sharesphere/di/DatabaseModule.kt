package com.example.sharesphere.di

import android.content.Context
import androidx.room.Room
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.util.Constants.HOME_POST_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ):HomePostDatabase{
        return Room.databaseBuilder(
            context,
            HomePostDatabase::class.java,
            HOME_POST_DATABASE
        ).build()
    }

}