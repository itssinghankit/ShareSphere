package com.example.sharesphere.domain.use_case.common

import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import javax.inject.Inject

class SetUpSocketListenerUseCase @Inject constructor(private val chatRepositoryInterface: ChatRepositoryInterface){
    suspend operator fun invoke(userId: String) {
        chatRepositoryInterface.setupSocketListeners(userId)
    }
}