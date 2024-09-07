package com.example.sharesphere.domain.model.user.profile

data class ViewAccountModel(
    val _id: String,
    val avatar: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val fullName: String,
    val username: String
)