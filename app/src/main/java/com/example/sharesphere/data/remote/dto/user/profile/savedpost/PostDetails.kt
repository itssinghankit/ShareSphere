package com.example.sharesphere.data.remote.dto.user.profile.savedpost

data class PostDetails(
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