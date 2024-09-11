package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.search.SearchResDto
import com.example.sharesphere.domain.model.user.common.UserItemModel

fun SearchResDto.toUserItemModelList(): List<UserItemModel> {
    return this.data.map {
        UserItemModel(
            _id = it._id,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username
        )
    }
}