package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.search.SearchResDto
import com.example.sharesphere.domain.model.user.search.SearchUserModel

fun SearchResDto.toSearchUserModelList(): List<SearchUserModel> {
    return this.data.map {
        SearchUserModel(
            _id = it._id,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username
        )
    }
}