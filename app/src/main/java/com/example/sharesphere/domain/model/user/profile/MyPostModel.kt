package com.example.sharesphere.domain.model.user.profile

data class MyPostModel (
    val _id: String,
    val caption: String,
    val commentCount: Int,
    val createdAt: String,
    val likeCount: Int,
    val postImages: List<String>
)