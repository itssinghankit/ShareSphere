package com.example.sharesphere.data.remote.dto.user.profile

data class SavedPostsDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)