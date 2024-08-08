package com.example.sharesphere.domain.model.auth

data class RegisterModel(
    val accessToken: String,
    val refreshToken: String,
    val email: String,
    val username: String
)
