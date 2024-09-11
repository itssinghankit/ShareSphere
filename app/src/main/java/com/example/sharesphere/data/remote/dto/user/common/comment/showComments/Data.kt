package com.example.sharesphere.data.remote.dto.user.common.comment.showComments

data class Data(
    val _id: String,
    val commentedBy: CommentedBy,
    val content: String,
    val createdAt: String
)