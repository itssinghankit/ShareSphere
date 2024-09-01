package com.example.sharesphere.data.commonDto.user.home.post

data class Data(
    val currentPage: Int,
    val posts: List<Post>,
    val totalPages: Int,
    val totalPosts: Int
)