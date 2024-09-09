package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.auth.avatar.AvatarResDto
import com.example.sharesphere.data.remote.dto.auth.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.auth.username.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepositoryInterface {

    suspend fun checkUsername(username: String): UsernameResponseDto

    suspend fun mobileSendOtp(mobile: String): MobileOtpResponseDto
    suspend fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto

    suspend fun register(registerRequestDto: RegisterRequestDto): RegisterResponseDto
    suspend fun verifyOtp(verifyOtpRequestDto: VerifyOtpRequestDto): VerifyOtpResponseDto
    suspend fun details(
        avatar: MultipartBody.Part,
        gender: RequestBody,
        bio: RequestBody,
        dob: RequestBody,
        fullName: RequestBody,
    ): AvatarResDto


}