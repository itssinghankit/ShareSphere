package com.example.sharesphere.data.remote.dto.chat.chat.oneone

data class LastMessage(
    val __v: Int,
    val _id: String,
    val attachments: List<Any>,
    val chat: String,
    val content: String,
    val createdAt: String,
    val seenBy: List<Any>,
    val sender: Sender,
    val updatedAt: String
)