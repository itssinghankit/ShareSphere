package com.example.sharesphere.data.repository

import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.SignInRequestDto
import com.example.sharesphere.data.remote.dto.SignInResponseDto
import com.example.sharesphere.data.remote.dto.UsernameResponseDto
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
}