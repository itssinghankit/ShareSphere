package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.avatar.AvatarResDto
import com.example.sharesphere.domain.model.auth.AvatarModel

fun AvatarResDto.toAvatarModel(): AvatarModel {
    return AvatarModel(
        avatar = this.data.avatar,
        bio = this.data.bio,
        dob = this.data.dob,
        fullName = this.data.fullName,
        gender = this.data.gender,
        isDetailsFilled = this.data.isDetailsFilled

    )
}