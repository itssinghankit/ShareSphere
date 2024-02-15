package com.example.sharesphere.di

import android.content.Context
import com.example.sharesphere.data.repository.datastore.DataStoreRepositoryImplementation
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context:Context): DataStoreRepositoryInterface {
        return DataStoreRepositoryImplementation(context)
    }

}