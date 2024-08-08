package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.signin.SignInResponseDto
import com.example.sharesphere.domain.model.auth.SignInModel

fun SignInResponseDto.toSignInModel(): SignInModel {
    return SignInModel(
        _id = this.data.user._id,
        avatar = this.data.user.avatar,
        bio = this.data.user.bio,
        dob = this.data.user.dob,
        email = this.data.user.email,
        fullName = this.data.user.fullName,
        gender = this.data.user.gender,
        isDetailsFilled = this.data.user.isDetailsFilled,
        isVerified = this.data.user.isVerified,
        mobile = this.data.user.mobile,
        username = this.data.user.username,
        accessToken = this.data.accessToken,
        refreshToken = this.data.refreshToken
    )
}