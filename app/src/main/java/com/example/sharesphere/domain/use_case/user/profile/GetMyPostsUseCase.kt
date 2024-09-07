package com.example.sharesphere.domain.use_case.user.profile

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class GetMyPostsUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke() = userRepositoryInterface.getMyPosts()
}