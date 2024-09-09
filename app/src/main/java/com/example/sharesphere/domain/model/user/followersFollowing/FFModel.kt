package com.example.sharesphere.domain.model.user.followersFollowing

data class FFModel(
    val _id: String,
    val avatar: String,
    val fullName: String,
    val isFollowed: Boolean,
    val username: String
)
