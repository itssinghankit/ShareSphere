package com.example.sharesphere.domain.repository

import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.remote.dto.user.home.like.LikePostDto
import com.example.sharesphere.data.remote.dto.user.home.save.SavePostDto
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    fun getAllPosts(): Flow<PagingData<Post>>
    suspend fun likePost(postId:String):Flow<ApiResult<LikePostDto,DataError.Network>>
    suspend fun savePost(postId: String):Flow<ApiResult<SavePostDto,DataError.Network>>
}