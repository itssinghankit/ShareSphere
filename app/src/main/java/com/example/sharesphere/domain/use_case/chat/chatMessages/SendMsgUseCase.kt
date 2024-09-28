package com.example.sharesphere.domain.use_case.chat.chatMessages

import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import javax.inject.Inject

class SendMsgUseCase @Inject constructor(private val chatRepositoryInterface: ChatRepositoryInterface) {
    suspend operator fun invoke(chatId: String, myUserId: String, content: String) =
        chatRepositoryInterface.sendMsg(chatId, myUserId, content)

}