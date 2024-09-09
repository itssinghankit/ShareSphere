package com.example.sharesphere.data.remote.dto.auth.verifyotp

data class VerifyOtpResponseDto(
    val `data`: VerifyOtpData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

data class VerifyOtpData(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val isVerified: Boolean,
    val mobile: Long,
    val updatedAt: String,
    val username: String
)