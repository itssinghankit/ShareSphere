package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.data.remote.dto.chat.chat.oneone.OneOneChat
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatRepositoryInterface {

    val newMessage: StateFlow<GetAllMessagesModel?>

    suspend fun setupSocketListeners(userId: String)
    suspend fun closeSocketConnection()
    suspend fun getChats(): Flow<ApiResult<List<Chat>, DataError.Network>>
    suspend fun createOrGetOneOneChat(receiverId: String): Flow<ApiResult<OneOneChat, DataError.Network>>
    suspend fun getAllMessages(
        chatId: String,
        myUserId: String
    ): Flow<ApiResult<List<GetAllMessagesModel>, DataError.Network>>

    suspend fun sendMsg(
        chatId: String,
        myUserId: String,
        content: String
    ): Flow<ApiResult<GetAllMessagesModel, DataError.Network>>


}