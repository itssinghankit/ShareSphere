package com.example.sharesphere.di

import com.example.sharesphere.BuildConfig
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.repository.AuthRepositoryImplementation
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesShareSphereInterface(retrofit: Retrofit): ShareSphereApi {
        return retrofit.create(ShareSphereApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(shareSphereApi: ShareSphereApi): AuthRepositoryInterface {
        return AuthRepositoryImplementation(shareSphereApi)
    }

}