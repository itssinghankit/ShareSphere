package com.example.sharesphere.data.remote

import com.example.sharesphere.BuildConfig
import com.example.sharesphere.data.remote.dto.auth.refreshToken.RefreshTokenRequestDto
import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject

class ShareSphereAuthenticator @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            dataStoreRepository.getString(PreferencesKeys.REFRESH_TOKEN).first()
        }
        return runBlocking {
            val accessToken = getNewToken(refreshToken)

            if (accessToken.isNullOrBlank()) { //Couldn't refresh the token, so restart the login process
                Timber.d("failed to refresh token")
                dataStoreRepository.deleteString(PreferencesKeys.REFRESH_TOKEN)
            }

            accessToken?.let {

                Timber.d(accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): String? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(ShareSphereApi::class.java)
        val generatedRefreshToken = refreshToken ?: ""

        //calling function is using runBlock to provide coroutine scope
        return try {
            val response = service.refreshToken(RefreshTokenRequestDto(generatedRefreshToken))
            Timber.d(response.toString())

            //if access token refreshed successfully save token to datastore
            dataStoreRepository.save(PreferencesKeys.ACCESS_TOKEN, response.data.accessToken)
            dataStoreRepository.save(PreferencesKeys.REFRESH_TOKEN, response.data.refreshToken)

            response.data.accessToken
        } catch (e: retrofit2.HttpException) {
            null
        }

    }
}