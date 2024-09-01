package com.example.sharesphere.data.commonDto.user.home.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sharesphere.util.Constants.POST_REMOTE_KEYS_TABLE

@Entity(tableName = POST_REMOTE_KEYS_TABLE)
data class PostRemoteKeysDto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
