package com.example.sharesphere.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.BuildConfig
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.ShareSphereAuthenticator
import com.example.sharesphere.data.remote.ShareSphereChatApi
import com.example.sharesphere.data.remote.ShareSphereInterceptor
import com.example.sharesphere.data.remote.SocketHandler
import com.example.sharesphere.data.repository.AuthRepositoryImplementation
import com.example.sharesphere.data.repository.ChatRepositoryImplementation
import com.example.sharesphere.data.repository.UserRepositoryImplementation
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Render

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Vercel

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
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    @Vercel
    fun providesRetrofitVercel(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    @Render
    fun providesRetrofitRender(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.RENDER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun providesShareSphereInterface(@Vercel retrofit: Retrofit): ShareSphereApi {
        return retrofit.create(ShareSphereApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(shareSphereApi: ShareSphereApi): AuthRepositoryInterface {
        return AuthRepositoryImplementation(shareSphereApi)
    }

    @Singleton
    @Provides
    fun providesUserRepository(
        shareSphereApi: ShareSphereApi,
        homePostDatabase: HomePostDatabase
    ): UserRepositoryInterface {
        return UserRepositoryImplementation(
            shareSphereApi = shareSphereApi,
            homePostDatabase = homePostDatabase
        )
    }

    @Singleton
    @Provides
    fun providesShareSphereChatInterface(@Render retrofit: Retrofit): ShareSphereChatApi {
        return retrofit
            .create(ShareSphereChatApi::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun providesChatRepository(
        shareSphereChatApi: ShareSphereChatApi,
        socketHandler: SocketHandler
    ): ChatRepositoryInterface {
        return ChatRepositoryImplementation(
            shareSphereChatApi = shareSphereChatApi,
            socketHandler = socketHandler
        )
    }

}