package com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg

data class SendMsgResDto(
    val `data`: SendMsgData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)