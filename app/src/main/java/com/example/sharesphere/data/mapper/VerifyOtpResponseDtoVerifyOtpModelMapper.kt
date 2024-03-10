package com.example.sharesphere.data.mapper

import com.example.sharesphere.data.remote.dto.verifyotp.VerifyOtpResponseDto
import com.example.sharesphere.domain.model.VerifyOtpModel

fun VerifyOtpResponseDto.toVerifyOtpModel():VerifyOtpModel{
    return VerifyOtpModel(
        isVerified = this.data.isVerified,
        mobile = this.data.mobile
    )
}