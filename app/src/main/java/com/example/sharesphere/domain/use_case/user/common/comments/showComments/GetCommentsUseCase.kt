package com.example.sharesphere.domain.use_case.user.common.comments.showComments

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(postId: String) = userRepositoryInterface.showComments(postId)
}