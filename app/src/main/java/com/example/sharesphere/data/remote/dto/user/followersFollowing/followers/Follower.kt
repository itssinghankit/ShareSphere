package com.example.sharesphere.data.remote.dto.user.followersFollowing.followers

data class Follower(
    val _id: String,
    val avatar: String,
    val followerId: String,
    val fullName: String,
    val isFollowed: Boolean,
    val username: String
)