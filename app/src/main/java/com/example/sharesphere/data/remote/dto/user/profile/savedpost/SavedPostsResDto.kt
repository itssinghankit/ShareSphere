package com.example.sharesphere.data.remote.dto.user.profile.savedpost

data class SavedPostsResDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)