package com.example.sharesphere.data.remote.dto

import com.example.sharesphere.domain.model.SignInModel

data class SignInResponseDto(
    val `data`: SignInData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

//TODO : mark the fields nullable
data class User(
    val __v: Int,
    val _id: String,
    val avatar: String,
    val bio: String,
    val createdAt: String,
    val dob: String,
    val email: String,
    val fullName: String,
    val gender: String,
    val isDetailsFilled: Boolean,
    val isVerified: Boolean,
    val mobile: Long,
    val updatedAt: String,
    val username: String
)

data class SignInData(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)
