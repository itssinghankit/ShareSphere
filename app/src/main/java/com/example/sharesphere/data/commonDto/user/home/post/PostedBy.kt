package com.example.sharesphere.data.commonDto.user.home.post

import androidx.room.ColumnInfo

data class PostedBy(
    @ColumnInfo(name = "user_id")
    val _id: String,
    val avatar: String,
    val fullName: String,
    val username: String
)