package com.example.sharesphere.domain.model

import com.example.sharesphere.data.remote.dto.User
import com.google.gson.annotations.SerializedName

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
    val mobile: String,
    val username: String,
    val accessToken: String,
    val refreshToken: String
)
