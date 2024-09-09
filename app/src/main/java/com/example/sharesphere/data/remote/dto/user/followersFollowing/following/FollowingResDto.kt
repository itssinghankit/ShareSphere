package com.example.sharesphere.data.remote.dto.user.followersFollowing.following

data class FollowingResDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)