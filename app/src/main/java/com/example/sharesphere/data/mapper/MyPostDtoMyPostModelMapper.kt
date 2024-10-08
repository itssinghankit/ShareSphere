package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.profile.myPost.MyPostResDto
import com.example.sharesphere.domain.model.user.profile.MyPostModel

fun MyPostResDto.toMyPostModelList():List<MyPostModel> {
    return this.data.map {
        MyPostModel(
            _id = it._id,
            commentCount = it.commentCount,
            likeCount = it.likeCount,
            postImages = it.postImages,
            caption = it.caption,
            createdAt = it.createdAt
        )
    }
}