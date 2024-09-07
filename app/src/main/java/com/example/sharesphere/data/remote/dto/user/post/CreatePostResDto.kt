package com.example.sharesphere.data.remote.dto.user.post

data class CreatePostResDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)