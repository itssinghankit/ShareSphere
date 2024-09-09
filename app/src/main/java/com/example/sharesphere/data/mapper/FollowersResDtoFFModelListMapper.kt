package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.followersFollowing.followers.FollowersResDto
import com.example.sharesphere.domain.model.user.followersFollowing.FFModel

fun FollowersResDto.toFFModelList(): List<FFModel> {
    return this.data.followers.map {
       FFModel(
            _id = it.followerId,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username
        )
    }
}