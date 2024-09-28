package com.example.sharesphere.domain.use_case.chat.chatMessages

import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewMsgUseCase @Inject constructor(
    private val chatRepositoryInterface: ChatRepositoryInterface
) {
    operator fun invoke(): Flow<GetAllMessagesModel?> = chatRepositoryInterface.newMessage
}