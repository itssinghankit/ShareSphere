package com.example.sharesphere.domain.use_case.user.home

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(postId:String)=userRepositoryInterface.likePost(postId)
}