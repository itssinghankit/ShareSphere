package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.avatar.AvatarResDto
import com.example.sharesphere.data.remote.dto.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.username.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpResponseDto
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