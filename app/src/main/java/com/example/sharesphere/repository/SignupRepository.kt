package com.example.sharesphere.repository

import android.util.Log
import com.example.sharesphere.common.ApiResponse
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject

class SignupRepository @Inject constructor(private val retrofitInterface: ShareSphereApi) {

    private var _signupResponseFlow =
        MutableStateFlow<ApiResponse<SignupResponse>>(ApiResponse.Loading())
    val signupResponseFlow: StateFlow<ApiResponse<SignupResponse>>
        get() = _signupResponseFlow

    suspend fun signup(email: String, password: String) {
        _signupResponseFlow.value= ApiResponse.Loading()
        try {
            val response = retrofitInterface.signup(SignupRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                _signupResponseFlow.emit(ApiResponse.Success(response.body()!!))
                Log.d("meow", "hello ${response.body()}")
                Log.d("meow", response.code().toString())
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _signupResponseFlow.emit(ApiResponse.Error(errObj.getString("message")))
            } else {
                _signupResponseFlow.emit(ApiResponse.Error("Something went wrong"))
            }

        } catch (e: Exception) {
            _signupResponseFlow.emit(ApiResponse.Error("Check your Internet connection"))
        }
    }
}