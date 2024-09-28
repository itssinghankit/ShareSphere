package com.example.sharesphere.domain.use_case.chat

import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import javax.inject.Inject

class CreateOrGetOneOneChatUseCase @Inject constructor(
    private val chatRepositoryInterface: ChatRepositoryInterface
) {
    suspend operator fun invoke(receiverId:String) =chatRepositoryInterface.createOrGetOneOneChat(receiverId)
}