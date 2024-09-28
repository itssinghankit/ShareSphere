package com.example.sharesphere.data.remote.dto.chat.chat.oneone

data class OneOneChat(
    val __v: Int,
    val _id: String,
    val admin: String,
    val createdAt: String,
    val groupChatDp: String,
    val isGroupChat: Boolean,
    val lastMessage: LastMessage,
    val name: String,
    val participants: List<Participant>,
    val updatedAt: String
)