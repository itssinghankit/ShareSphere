package com.example.sharesphere.repository

import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import com.example.sharesphere.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class SignupRepository @Inject constructor(private val retrofitInterface: ShareSphereApi) {

    private var _signupResponseFlow =
        MutableStateFlow<ApiResponse<SignupResponse>>(ApiResponse.Loading())
    val signupResponseFlow: StateFlow<ApiResponse<SignupResponse>>
        get() = _signupResponseFlow

    suspend fun signup(email: String, password: String) {
        _signupResponseFlow.value = ApiResponse.Loading()
        try {
            val response = retrofitInterface.signup(SignupRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                _signupResponseFlow.emit(ApiResponse.Success(response.body()!!))
                Timber.d("hello " + response.body())
                Timber.d(response.code().toString())
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _signupResponseFlow.emit(ApiResponse.Error(UiText.DynamicString("Check your Internet connection")))
            } else {
                _signupResponseFlow.emit(ApiResponse.Error(UiText.DynamicString("Something went wrong")))
            }

        } catch (e: Exception) {
            _signupResponseFlow.emit(ApiResponse.Error(UiText.DynamicString("Check your Internet connection")))
        }
    }
}