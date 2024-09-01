package com.example.sharesphere.domain.repository

import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInterface {

    fun getAllPosts(): Flow<PagingData<Post>>
}