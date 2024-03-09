package com.example.sharesphere.domain.model

import com.example.sharesphere.data.remote.dto.register.RegisterUser

data class RegisterModel(
    val accessToken: String,
    val refreshToken: String,
    val email: String,
    val username: String
)
