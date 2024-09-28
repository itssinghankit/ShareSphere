package com.example.sharesphere.domain.use_case.chat.chatMessages

import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import javax.inject.Inject


class GetAllMessagesUseCase @Inject constructor(private val chatRepositoryInterface: ChatRepositoryInterface) {
    suspend operator fun invoke(chatId: String, myUserId: String) =
        chatRepositoryInterface.getAllMessages(chatId, myUserId)
}