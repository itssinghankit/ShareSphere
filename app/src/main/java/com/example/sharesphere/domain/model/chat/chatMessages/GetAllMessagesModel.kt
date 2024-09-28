package com.example.sharesphere.domain.model.chat.chatMessages

data class GetAllMessagesModel(
    val _id: String,
    val attachments: List<Any>,
    val content: String,
    val seenBy: List<Any>,
    val updatedAt: String,
    val senderId: String,
    val senderAvatar: String,
    val fullName:String,
    val isOwner:Boolean,
    val chatId:String
)
