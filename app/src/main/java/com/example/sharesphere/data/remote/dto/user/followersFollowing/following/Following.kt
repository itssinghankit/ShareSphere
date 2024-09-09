package com.example.sharesphere.data.remote.dto.user.followersFollowing.following

data class Following(
    val _id: String,
    val avatar: String,
    val followingId: String,
    val fullName: String,
    val isFollowed: Boolean,
    val username: String
)