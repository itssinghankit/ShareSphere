package com.example.sharesphere.data.remote

import com.example.sharesphere.data.commonDto.user.home.post.PostsDto
import com.example.sharesphere.data.remote.dto.auth.avatar.AvatarResDto
import com.example.sharesphere.data.remote.dto.auth.mobile.MobileOtpRequestDto
import com.example.sharesphere.data.remote.dto.auth.mobile.MobileOtpResponseDto
import com.example.sharesphere.data.remote.dto.auth.refreshToken.RefreshTokenRequestDto
import com.example.sharesphere.data.remote.dto.auth.refreshToken.RefreshTokenResponseDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterRequestDto
import com.example.sharesphere.data.remote.dto.auth.register.RegisterResponseDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInRequestDto
import com.example.sharesphere.data.remote.dto.auth.signin.SignInResponseDto
import com.example.sharesphere.data.remote.dto.user.home.like.LikePostDto
import com.example.sharesphere.data.remote.dto.user.home.save.SavePostDto
import com.example.sharesphere.data.remote.dto.user.post.CreatePostResDto
import com.example.sharesphere.data.remote.dto.user.profile.savedpost.SavedPostsResDto
import com.example.sharesphere.data.remote.dto.user.profile.myPost.MyPostResDto
import com.example.sharesphere.data.remote.dto.user.profile.viewProfile.ViewAccountResDto
import com.example.sharesphere.data.remote.dto.auth.username.UsernameResponseDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpRequestDto
import com.example.sharesphere.data.remote.dto.auth.verifyotp.VerifyOtpResponseDto
import com.example.sharesphere.data.remote.dto.chat.chat.GetChatsResDto
import com.example.sharesphere.data.remote.dto.user.common.comment.addComments.AddCommentReqDto
import com.example.sharesphere.data.remote.dto.user.common.comment.addComments.AddCommentResDto
import com.example.sharesphere.data.remote.dto.user.common.comment.showComments.ShowCommentsResDto
import com.example.sharesphere.data.remote.dto.user.common.follow.FollowUserResDto
import com.example.sharesphere.data.remote.dto.user.followersFollowing.followers.FollowersResDto
import com.example.sharesphere.data.remote.dto.user.followersFollowing.following.FollowingResDto
import com.example.sharesphere.data.remote.dto.user.search.SearchResDto
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

    @Headers("AddAuthorizationHeader: true")
    @POST("post/like-post/{postId}")
    suspend fun likePost(@Path("postId") postId: String):LikePostDto

    @Headers("AddAuthorizationHeader: true")
    @POST("post/save-post/{postId}")
    suspend fun savePost(@Path("postId") postId: String):SavePostDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/view-account/{userId}")
    suspend fun viewAccount(@Path("userId") userId: String): ViewAccountResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/get-my-posts")
    suspend fun getMyPosts(): MyPostResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/show-saved-post")
    suspend fun getSavedPosts(): SavedPostsResDto

    @Multipart
    @Headers("AddAuthorizationHeader: true")
    @POST("post/create-post")
    suspend fun createPost(
        @Part postImages: List<MultipartBody.Part>,
        @Part("caption") caption: RequestBody
    ): CreatePostResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/search-user/{username}")
    suspend fun searchUser(
        @Path("username") username:String
    ): SearchResDto

    @Headers("AddAuthorizationHeader: true")
    @POST("post/follow-account/{accountId}")
    suspend fun followUser(
        @Path("accountId") accountId: String
    ): FollowUserResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/view-account-followers/{userId}")
    suspend fun getFollowers(
        @Path("userId") userId: String
    ):FollowersResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/view-account-following/{userId}")
    suspend fun getFollowing(
        @Path("userId") userId: String
    ):FollowingResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("post/show-comments/{postId}")
    suspend fun showComments(
        @Path("postId") postId: String
    ): ShowCommentsResDto

    @Headers("AddAuthorizationHeader: true")
    @POST("post/comment/{postId}")
    suspend fun addComment(
        @Path("postId") postId: String,
        @Body addCommentReqDto: AddCommentReqDto
    ): AddCommentResDto

    @Headers("AddAuthorizationHeader: true")
    @GET("chat/all")
    suspend fun getChats():GetChatsResDto


}