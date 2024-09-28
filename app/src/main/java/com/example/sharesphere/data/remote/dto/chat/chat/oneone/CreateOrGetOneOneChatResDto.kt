package com.example.sharesphere.data.remote.dto.chat.chat.oneone

data class CreateOrGetOneOneChatResDto(
    val `data`: OneOneChat,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)