package com.example.sharesphere.repository

import android.util.Log
import com.example.sharesphere.api.RetrofitInterface
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignupRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    private val _signupResponse = MutableStateFlow<SignupResponse>(SignupResponse())
    val signupResponse: StateFlow<SignupResponse>
        get() = _signupResponse

    suspend fun signup(email: String, password: String) {
        val signupObject = SignupRequest(email, password)
        val response = retrofitInterface.signup(signupObject)
        if (response.isSuccessful && response.body() != null) {
            _signupResponse.emit(response.body()!!)
            Log.d("meow","hello${response.body()}")
        }
    }

}