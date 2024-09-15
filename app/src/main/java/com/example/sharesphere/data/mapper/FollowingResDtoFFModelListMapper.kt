package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.followersFollowing.following.FollowingResDto
import com.example.sharesphere.domain.model.user.common.UserItemModel

fun FollowingResDto.toUserItemModelList(): List<UserItemModel> {
    return this.data.following.map {
       UserItemModel(
            _id = it.followingId,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username,
           isOnline = it.isOnline
        )
    }
}