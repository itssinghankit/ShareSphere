package com.example.sharesphere.data.remote.dto.user.common.follow

data class FollowUserResDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)