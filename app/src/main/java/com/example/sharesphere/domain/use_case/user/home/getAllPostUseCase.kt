package com.example.sharesphere.domain.use_case.user.home

import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetAllPostUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {

    operator fun invoke(): Flow<PagingData<Post>> {
        Timber.d("USe case")
        return userRepositoryInterface.getAllPosts()
    }
}