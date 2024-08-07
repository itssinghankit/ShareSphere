package com.example.sharesphere.di

import com.example.sharesphere.BuildConfig
import com.example.sharesphere.data.remote.ShareSphereAuthenticator
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.ShareSphereInterceptor
import com.example.sharesphere.data.repository.AuthRepositoryImplementation
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: ShareSphereInterceptor,
        authAuthenticator: ShareSphereAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
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