package com.example.sharesphere.data.repository

import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.auth.avatar.AvatarReqDto
import com.example.sharesphere.data.remote.dto.auth.avatar.AvatarResDto
import com.example.sharesphere.data.remote.dto.auth.mobile.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.auth.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.auth.username.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpResponseDto
import com.example.sharesphere.domain.repository.AuthRepositoryInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override suspend fun verifyOtp(verifyOtpRequestDto: VerifyOtpRequestDto): VerifyOtpResponseDto {
        return shareSphereApi.verifyOtp(verifyOtpRequestDto)
    }

    override suspend fun details(
        avatar: MultipartBody.Part,
        gender: RequestBody,
        bio: RequestBody,
        dob: RequestBody,
        fullName: RequestBody
    ): AvatarResDto {
        return shareSphereApi.details(avatar,fullName, dob, bio, gender)
    }
}