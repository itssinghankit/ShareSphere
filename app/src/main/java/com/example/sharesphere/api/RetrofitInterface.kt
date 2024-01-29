package com.example.sharesphere.api

import com.example.sharesphere.models.SigninRequest
import com.example.sharesphere.models.SigninResponse
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import com.example.sharesphere.models.checkUsername.CheckUsernameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {

    @POST("/auth/signup")
    suspend fun signup(@Body signupRequest: SignupRequest):Response<SignupResponse>

    @POST("/auth/signin")
    suspend fun signin(@Body signinRequest: SigninRequest):Response<SigninResponse>

    @GET("user/check-username/{username}")
    suspend fun checkUsername(@Path("username") username:String):Response<CheckUsernameResponse>

    //{"statusCode":404,"data":null,"success":false,"errors":{"message":"Not Found"}}
}