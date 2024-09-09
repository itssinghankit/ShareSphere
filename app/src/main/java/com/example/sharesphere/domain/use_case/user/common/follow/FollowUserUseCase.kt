package com.example.sharesphere.domain.use_case.user.common.follow

import com.example.sharesphere.data.repository.UserRepositoryImplementation
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(accountId:String)=userRepositoryInterface.followUser(accountId)
}