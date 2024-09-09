package com.example.sharesphere.data.remote.dto.auth.verifyotp

data class VerifyOtpRequestDto(
    val emailOTP: String,
    val mobileOTP: String
)