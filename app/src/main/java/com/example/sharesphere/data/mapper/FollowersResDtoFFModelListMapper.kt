package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.followersFollowing.followers.FollowersResDto
import com.example.sharesphere.domain.model.user.common.UserItemModel

fun FollowersResDto.toUserItemModelList(): List<UserItemModel> {
    return this.data.followers.map {
       UserItemModel(
            _id = it.followerId,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username
        )
    }
}