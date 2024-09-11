package com.example.sharesphere.domain.use_case.user.common.comments.addComment

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(postId: String, content: String) =
        userRepositoryInterface.addComment(postId, content)
}