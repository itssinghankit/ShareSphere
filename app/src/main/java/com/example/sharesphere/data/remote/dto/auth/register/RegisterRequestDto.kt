package com.example.sharesphere.data.remote.dto.auth.register

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val username: String
)