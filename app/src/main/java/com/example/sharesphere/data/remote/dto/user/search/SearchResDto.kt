package com.example.sharesphere.data.remote.dto.user.search

data class SearchResDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)