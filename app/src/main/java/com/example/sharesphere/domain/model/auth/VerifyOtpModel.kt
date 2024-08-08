package com.example.sharesphere.domain.model.auth

data class VerifyOtpModel(
    val isVerified: Boolean,
    val mobile: Long,
)
