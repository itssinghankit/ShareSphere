package com.example.sharesphere.domain.repository

import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.remote.dto.user.home.like.LikePostDto
import com.example.sharesphere.data.remote.dto.user.home.save.SavePostDto
import com.example.sharesphere.data.remote.dto.user.post.CreatePostResDto
import com.example.sharesphere.domain.model.user.common.ShowCommentsModel
import com.example.sharesphere.domain.model.user.common.UserItemModel
import com.example.sharesphere.domain.model.user.profile.MyPostModel
import com.example.sharesphere.domain.model.user.profile.SavedPostModel
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserRepositoryInterface {

    fun getAllPosts(): Flow<PagingData<Post>>
    suspend fun likePost(postId: String): Flow<ApiResult<LikePostDto, DataError.Network>>
    suspend fun savePost(postId: String): Flow<ApiResult<SavePostDto, DataError.Network>>
    suspend fun viewAccount(userId: String): Flow<ApiResult<ViewAccountModel, DataError.Network>>
    suspend fun getMyPosts(): Flow<ApiResult<List<MyPostModel>, DataError.Network>>
    suspend fun getSavedPosts():Flow<ApiResult<List<SavedPostModel>,DataError.Network>>
    suspend fun createPost(
        postImages: List<MultipartBody.Part>,
        caption: RequestBody,
    ):Flow<ApiResult<CreatePostResDto,DataError.Network>>
    suspend fun searchUser(usernameOrName:String):Flow<ApiResult<List<UserItemModel>,DataError.Network>>
    suspend fun followUser(accountId:String):Flow<ApiResult<Unit,DataError.Network>>
    suspend fun getFollowers(userId:String):Flow<ApiResult<List<UserItemModel>,DataError.Network>>
    suspend fun getFollowing(userId:String):Flow<ApiResult<List<UserItemModel>,DataError.Network>>
    suspend fun showComments(postId:String):Flow<ApiResult<List<ShowCommentsModel>,DataError.Network>>
    suspend fun addComment(postId:String,content:String):Flow<ApiResult<Unit,DataError.Network>>
}