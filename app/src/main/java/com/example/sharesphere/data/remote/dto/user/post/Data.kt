package com.example.sharesphere.data.remote.dto.user.post

data class Data(
    val __v: Int,
    val _id: String,
    val caption: String,
    val commentCount: Int,
    val createdAt: String,
    val likeCount: Int,
    val postImages: List<String>,
    val postedBy: String,
    val updatedAt: String
)