package com.example.sharesphere.data.remote.dto.refreshToken

data class RefreshTokenResponseDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)