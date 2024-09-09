package com.example.sharesphere.data.remote.dto.auth.mobile

import com.example.sharesphere.domain.model.auth.MobileModel

data class MobileOtpResponseDto(
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

fun MobileOtpResponseDto.toMobileModel(): MobileModel {
    return MobileModel(success = success,message=message)
}