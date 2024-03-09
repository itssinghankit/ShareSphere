package com.example.sharesphere.data.remote.dto.error

data class ServerErrorDto(
    val `data`: Any,
    val errors: Errors,
    val statusCode: Int,
    val success: Boolean
)
data class Errors(
    val message: String
)