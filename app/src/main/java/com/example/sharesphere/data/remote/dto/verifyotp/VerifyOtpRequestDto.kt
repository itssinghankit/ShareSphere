package com.example.sharesphere.data.remote.dto.verifyotp

data class VerifyOtpRequestDto(
    val emailOTP: String,
    val mobileOTP: String
)