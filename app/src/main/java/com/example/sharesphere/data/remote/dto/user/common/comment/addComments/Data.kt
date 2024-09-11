package com.example.sharesphere.data.remote.dto.user.common.comment.addComments

data class Data(
    val __v: Int,
    val _id: String,
    val commentedBy: String,
    val content: String,
    val createdAt: String,
    val postId: String,
    val updatedAt: String
)