package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.followersFollowing.followers.FollowersResDto
import com.example.sharesphere.data.remote.dto.user.followersFollowing.following.FollowingResDto
import com.example.sharesphere.domain.model.user.followersFollowing.FFModel

fun FollowingResDto.toFFModelList(): List<FFModel> {
    return this.data.following.map {
       FFModel(
            _id = it.followingId,
            avatar = it.avatar,
            fullName = it.fullName,
            isFollowed = it.isFollowed,
            username = it.username
        )
    }
}