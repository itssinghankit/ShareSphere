package com.example.sharesphere.repository

import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.models.SigninRequest
import com.example.sharesphere.models.SigninResponse
import com.example.sharesphere.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class SigninRepository @Inject constructor(private val retrofitInterface: ShareSphereApi) {

    private val _signinResponseFlow = MutableStateFlow<ApiResponse<SigninResponse>>(ApiResponse.Loading(isLoading = true))
    val signinResponse: MutableStateFlow<ApiResponse<SigninResponse>>
        get() = _signinResponseFlow
    suspend fun singin(email: String, password: String) {
        _signinResponseFlow.emit(ApiResponse.Loading())
        try {
            val response = retrofitInterface.signin(SigninRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                _signinResponseFlow.emit(ApiResponse.Success(response.body()!!))
                Timber.tag("meow").d("hello " + response.body())
                Timber.tag("meow").d(response.code().toString())
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _signinResponseFlow.emit(ApiResponse.Error(UiText.DynamicString(errObj.getString("message"))))
            } else {
                _signinResponseFlow.emit(ApiResponse.Error(UiText.DynamicString("something went wrong")))
            }

        } catch (e: Exception) {
            //we can also use _signinResponseFlow.value = ApiResponse.Error("Check your Internet connection")
            _signinResponseFlow.emit(ApiResponse.Error(UiText.DynamicString("Check your Internet connection")))
        }
    }

}