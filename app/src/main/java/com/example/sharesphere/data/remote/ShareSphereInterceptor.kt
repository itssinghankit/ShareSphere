package com.example.sharesphere.data.remote

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class ShareSphereInterceptor @Inject constructor(
    private val dataStoreRepositoryInterface: DataStoreRepositoryInterface,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        Timber.d("intercepted")

        if (request.headers["AddAuthorizationHeader"] == "true") {
            val token = runBlocking {
                dataStoreRepositoryInterface.getString(PreferencesKeys.AccessToken).first()
            }
            request.newBuilder().addHeader("Authorization", "Bearer $token")

        }
        val removedAddAuthHeaderRequest =
            request.newBuilder().removeHeader("AddAuthorizationHeader")
        return chain.proceed(removedAddAuthHeaderRequest.build())
    }
}