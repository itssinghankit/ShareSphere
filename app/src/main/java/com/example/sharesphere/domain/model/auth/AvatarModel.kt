package com.example.sharesphere.domain.model.auth

data class AvatarModel(
    val avatar: String,
    val bio: String,
    val dob: String,
    val fullName: String,
    val gender: String,
    val isDetailsFilled: Boolean
)
