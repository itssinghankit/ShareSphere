package com.example.sharesphere.api

import com.example.sharesphere.models.SigninRequest
import com.example.sharesphere.models.SigninResponse
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("/auth/signup")
    suspend fun signup(@Body signupRequest: SignupRequest):Response<SignupResponse>

    @POST("/auth/signin")
    suspend fun signin(@Body signinRequest: SigninRequest):Response<SigninResponse>
}