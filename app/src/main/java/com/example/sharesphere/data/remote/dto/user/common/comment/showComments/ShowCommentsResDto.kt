package com.example.sharesphere.data.remote.dto.user.common.comment.showComments

data class ShowCommentsResDto(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)