package com.example.sharesphere.repository

import android.util.Log
import com.example.sharesphere.api.RetrofitInterface
import com.example.sharesphere.models.SigninRequest
import com.example.sharesphere.models.SigninResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SigninRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    private val _signinResponse = MutableStateFlow<SigninResponse>(SigninResponse())
    val signinResponse: StateFlow<SigninResponse>
        get() = _signinResponse


    suspend fun singin(email: String, password: String) {
        val signinReqObj = SigninRequest(email, password)
        val response = retrofitInterface.signin(signinReqObj)
        if (response.isSuccessful && response.body() != null) {
            _signinResponse.emit(response.body()!!)
            Log.d("meow", response.body().toString())
        }
    }

}