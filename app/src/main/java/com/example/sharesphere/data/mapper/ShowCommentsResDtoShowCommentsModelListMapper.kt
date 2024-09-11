package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.common.comment.showComments.ShowCommentsResDto
import com.example.sharesphere.domain.model.user.common.ShowCommentsModel

fun ShowCommentsResDto.toShowCommentsModelList(): List<ShowCommentsModel> {
    return this.data.map {
        ShowCommentsModel(
            _id = it.commentedBy._id,
            avatar = it.commentedBy.avatar,
            fullName = it.commentedBy.fullName,
            username = it.commentedBy.username,
            content = it.content
        )
    }
}