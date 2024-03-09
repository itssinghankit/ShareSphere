package com.example.sharesphere.data.repository

import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.mobile.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.username.UsernameResponseDto
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImplementation @Inject constructor(private val shareSphereApi: ShareSphereApi):
    AuthRepositoryInterface {

    override suspend fun checkUsername(username: String): UsernameResponseDto {
        return shareSphereApi.checkUsername(username)
    }

    override suspend fun mobileSendOtp(mobile: String): MobileOtpResponseDto {
        return shareSphereApi.mobileSendOtp(MobileOtpRequestDto(mobile=mobile))
    }

    override suspend fun signIn(signInRequestDto: SignInRequestDto): SignInResponseDto {
        return shareSphereApi.signIn(signInRequestDto)
    }

    override suspend fun register(registerRequestDto: RegisterRequestDto): RegisterResponseDto {
        return shareSphereApi.register(registerRequestDto)
    }
}