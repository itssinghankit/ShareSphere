package com.example.sharesphere.data.commonDto.user.home.post

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sharesphere.data.local.ListStringConverter
import com.example.sharesphere.util.Constants.POST_IMAGE_TABLE


@Entity(tableName = POST_IMAGE_TABLE)
@TypeConverters(ListStringConverter::class)
data class Post(
    val __v: Int,
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val caption: String,
    val commentCount: Int,
    val createdAt: String,
    val isFollowed: Boolean,
    val isSaved: Boolean,
    var isLiked: Boolean,
    val likeCount: Int,
    val postImages: List<String>,
    @Embedded
    val postedBy: PostedBy,
    val updatedAt: String
)