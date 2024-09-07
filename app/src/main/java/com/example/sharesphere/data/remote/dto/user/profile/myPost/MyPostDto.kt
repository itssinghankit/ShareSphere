package com.example.sharesphere.data.remote.dto.user.profile.myPost

data class MyPostDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)