package com.example.sharesphere.models

data class SigninResponse(
    val email: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)