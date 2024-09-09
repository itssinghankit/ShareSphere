package com.example.sharesphere.domain.model.user.search

data class SearchUserModel(
    val _id: String,
    val avatar: String,
    val fullName: String,
    val isFollowed: Boolean,
    val username: String
)