package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.register.RegisterResponseDto
import com.example.sharesphere.domain.model.auth.RegisterModel

fun RegisterResponseDto.toRegisterModel(): RegisterModel {
    return RegisterModel(
        accessToken = this.data.accessToken,
        refreshToken = this.data.refreshToken,
        email = this.data.user.email,
        username = this.data.user.username)
}