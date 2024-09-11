package com.example.sharesphere.domain.use_case.user.common.comments.accountDetails

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class ViewAccountUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(userId: String) = userRepositoryInterface.viewAccount(userId)
}