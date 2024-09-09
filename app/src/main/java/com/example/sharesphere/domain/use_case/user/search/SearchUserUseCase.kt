package com.example.sharesphere.domain.use_case.user.search

import com.example.sharesphere.domain.repository.UserRepositoryInterface
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepositoryInterface: UserRepositoryInterface
) {
    suspend operator fun invoke(usernameOrName: String) = userRepositoryInterface.searchUser(usernameOrName)
}