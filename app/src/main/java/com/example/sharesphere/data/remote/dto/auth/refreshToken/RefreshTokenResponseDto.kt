package com.example.sharesphere.data.remote.dto.auth.refreshToken

data class RefreshTokenResponseDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)