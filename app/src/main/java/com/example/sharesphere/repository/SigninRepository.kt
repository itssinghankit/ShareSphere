package com.example.sharesphere.repository

import android.util.Log
import com.example.sharesphere.api.ApiResponse
import com.example.sharesphere.api.RetrofitInterface
import com.example.sharesphere.models.SigninRequest
import com.example.sharesphere.models.SigninResponse
import com.example.sharesphere.models.SignupRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject

class SigninRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    private val _signinResponseFlow = MutableStateFlow<ApiResponse<SigninResponse>>(ApiResponse.Initial())
    val signinResponse: MutableStateFlow<ApiResponse<SigninResponse>>
        get() = _signinResponseFlow

    suspend fun singin(email: String, password: String) {
        _signinResponseFlow.emit(ApiResponse.Loading())
        try {
            val response = retrofitInterface.signin(SigninRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                _signinResponseFlow.emit(ApiResponse.Success(response.body()!!))
                Log.d("meow", "hello ${response.body()}")
                Log.d("meow", response.code().toString())
            } else if (response.errorBody() != null) {
                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                _signinResponseFlow.emit(ApiResponse.Error(errObj.getString("message")))
            } else {
                _signinResponseFlow.emit(ApiResponse.Error("Something went wrong"))
            }

        } catch (e: Exception) {
            //we can also use _signinResponseFlow.value = ApiResponse.Error("Check your Internet connection")
            _signinResponseFlow.emit(ApiResponse.Error("Check your Internet connection"))
        }
    }

}