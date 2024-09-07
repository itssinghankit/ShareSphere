package com.example.sharesphere.data.remote.dto.user.profile.myPost

data class MyPostResDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)