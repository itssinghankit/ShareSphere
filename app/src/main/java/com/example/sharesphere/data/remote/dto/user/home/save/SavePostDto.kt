package com.example.sharesphere.data.remote.dto.user.home.save

data class SavePostDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)