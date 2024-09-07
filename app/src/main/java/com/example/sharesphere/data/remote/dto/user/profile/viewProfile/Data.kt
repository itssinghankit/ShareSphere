package com.example.sharesphere.data.remote.dto.user.profile.viewProfile

data class Data(
    val _id: String,
    val avatar: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val fullName: String,
    val gender: String,
    val isFollowed: Boolean,
    val username: String
)