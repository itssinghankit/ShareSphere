package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.SignInRequestDto
import com.example.sharesphere.data.remote.dto.SignInResponseDto
import com.example.sharesphere.data.remote.dto.UsernameResponseDto

interface AuthRepositoryInterface {

    suspend fun checkUsername(username:String):UsernameResponseDto

    suspend fun mobileSendOtp(mobile:String): MobileOtpResponseDto
    suspend fun signIn(signInRequestDto: SignInRequestDto):SignInResponseDto


}