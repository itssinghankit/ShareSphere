package com.example.sharesphere.data.remote.dto.user.profile.viewProfile

data class ViewAccountDto(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)