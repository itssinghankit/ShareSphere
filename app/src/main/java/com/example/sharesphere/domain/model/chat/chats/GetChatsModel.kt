package com.example.sharesphere.domain.model.chat.chats

import com.example.sharesphere.data.remote.dto.chat.chat.LastMessage
import com.example.sharesphere.data.remote.dto.chat.chat.Participant

data class GetChatsModel(
    val chatId: String,
    val admin: String,
    val createdAt: String,
    val groupChatDp: String,
    val isGroupChat: Boolean,
    val senderId: String,
    val avatar: String,
    val email: String,
    val username: String,
    val lastMessage: LastMessage,
    val name: String,
    val participants: List<Participant>,
    val updatedAt: String
)
