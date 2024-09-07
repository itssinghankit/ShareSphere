package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.user.profile.viewProfile.ViewAccountResDto
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel

fun ViewAccountResDto.toViewAccountModel(): ViewAccountModel {
    return ViewAccountModel(
        _id = this.data._id,
        avatar = this.data.avatar,
        bio = this.data.bio,
        followers = this.data.followers,
        following = this.data.following,
        fullName = this.data.fullName,
        username = this.data.username
    )
}