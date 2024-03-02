package com.example.sharesphere.data.remote

import com.example.sharesphere.data.remote.dto.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.SignInRequestDto
import com.example.sharesphere.data.remote.dto.SignInResponseDto
import com.example.sharesphere.models.SignupRequest
import com.example.sharesphere.models.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ShareSphereApi {

    @POST("auth/signup")
    suspend fun signup(@Body signupRequest: SignupRequest):Response<SignupResponse>

    @POST("user/signin")
    suspend fun signIn(@Body signInRequest: SignInRequestDto):SignInResponseDto

    @GET("user/check-username/{username}")
    suspend fun checkUsername(@Path("username") username:String):UsernameResponseDto

    //{"statusCode":404,"data":null,"success":false,"errors":{"message":"Not Found"}}

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWIyODA1MmQ0MzM2NGZkNzYyNTE0NmIiLCJ1c2VybmFtZSI6Iml0c3NpbmdoYW5raXQiLCJlbWFpbCI6InNpbmdoYW5raXQua3JAZ21haWwuY29tIiwiZnVsbE5hbWUiOiJBbmtpdCBTaW5naCIsImlhdCI6MTcwOTIwMzc4NCwiZXhwIjoxNzA5MjkwMTg0fQ.dztHa2oQcbPUXkvw9YWNMhSjwYx332EV3S1E7zBh_l8")
    @POST("user/send-otp")
    suspend fun mobileSendOtp(@Body mobileOtpRequestDto: MobileOtpRequestDto):MobileOtpResponseDto



}