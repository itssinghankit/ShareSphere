package com.example.sharesphere.data.remote

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class ShareSphereInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val requestBuilder=request.newBuilder()
        Timber.d("intercepted")

        if (request.headers["AddAuthorizationHeader"] == "true") {
            val token = runBlocking {
                dataStoreRepository.getString(PreferencesKeys.ACCESS_TOKEN).first()

            }
            Timber.d("$token")
            requestBuilder.addHeader("Authorization", "Bearer $token")

        }
        requestBuilder.removeHeader("AddAuthorizationHeader")
        return chain.proceed(requestBuilder.build())
    }
}