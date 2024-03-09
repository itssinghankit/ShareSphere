package com.example.sharesphere.data.remote.dto.register

data class RegisterResponseDto(
    val `data`: RegisterData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)
data class RegisterData(
    val accessToken: String,
    val refreshToken: String,
    val user: RegisterUser
)
data class RegisterUser(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val updatedAt: String,
    val username: String
)