package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.username.UsernameResponseDto

interface AuthRepositoryInterface {

    suspend fun checkUsername(username:String): UsernameResponseDto

    suspend fun mobileSendOtp(mobile:String): MobileOtpResponseDto
    suspend fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto

    suspend fun register(registerRequestDto: RegisterRequestDto):RegisterResponseDto


}