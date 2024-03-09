package com.example.sharesphere.domain.model

data class SignInModel(
    val _id: String,
    val avatar: String,
    val bio: String,
    val dob: String,
    val email: String,
    val fullName: String,
    val gender: String,
    val isDetailsFilled: Boolean,
    val isVerified: Boolean,
    val mobile: Long,
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
