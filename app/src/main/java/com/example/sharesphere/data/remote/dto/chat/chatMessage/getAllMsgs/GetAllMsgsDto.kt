package com.example.sharesphere.data.remote.dto.chat.chatMessage.getAllMsgs

data class GetAllMsgsDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)