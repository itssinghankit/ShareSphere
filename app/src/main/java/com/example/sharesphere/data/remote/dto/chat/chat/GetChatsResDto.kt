package com.example.sharesphere.data.remote.dto.chat.chat

data class GetChatsResDto(
    val `data`: List<Chat>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)