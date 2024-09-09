package com.example.sharesphere.domain.model.user.followersFollowing

data class UserItemModel(
    val _id: String,
    val avatar: String,
    val fullName: String,
    val isFollowed: Boolean,
    val username: String
)