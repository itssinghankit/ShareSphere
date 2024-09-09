package com.example.sharesphere.domain.use_case.user.followersFollowing

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(userId:String)= userRepositoryInterface.getFollowers(userId)
}