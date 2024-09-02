package com.example.sharesphere.data.remote.dto.user.home.like

data class LikePostDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)