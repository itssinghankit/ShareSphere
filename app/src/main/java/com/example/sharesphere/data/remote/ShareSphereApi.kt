package com.example.sharesphere.data.remote

import com.example.sharesphere.data.commonDto.user.home.post.PostsDto
import com.example.sharesphere.data.remote.dto.avatar.AvatarResDto
import com.example.sharesphere.data.remote.dto.mobile.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.refreshToken.RefreshTokenRequestDto
import com.example.sharesphere.data.remote.dto.refreshToken.RefreshTokenResponseDto
import com.example.sharesphere.data.remote.dto.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.username.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ShareSphereApi {

    @POST("user/signup")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): RegisterResponseDto

    @Headers("AddAuthorizationHeader: false")
    @POST("user/signin")
    suspend fun signIn(@Body signInRequest: SignInRequestDto): SignInResponseDto

    @Headers("AddAuthorizationHeader: false")
    @POST("user/refresh-token")
    suspend fun refreshToken(@Body refreshTokenRequestDto: RefreshTokenRequestDto): RefreshTokenResponseDto

    @Headers("AddAuthorizationHeader: false")
    @GET("user/check-username/{username}")
    suspend fun checkUsername(@Path("username") username: String): UsernameResponseDto

    @Headers("AddAuthorizationHeader: true")
    @POST("user/send-otp")
    suspend fun mobileSendOtp(@Body mobileOtpRequestDto: MobileOtpRequestDto): MobileOtpResponseDto

    @Headers("AddAuthorizationHeader: true")
    @POST("user/verify-otp")
    suspend fun verifyOtp(@Body verifyOtpRequestDto: VerifyOtpRequestDto): VerifyOtpResponseDto

    @Multipart
    @Headers("AddAuthorizationHeader: true")
    @POST("user/details")
    suspend fun details(
        @Part avatar: MultipartBody.Part,
        @Part("fullName") fullName: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("gender") gender: RequestBody,
    ): AvatarResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/get-all-posts")
    suspend fun getAllPosts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): PostsDto


}