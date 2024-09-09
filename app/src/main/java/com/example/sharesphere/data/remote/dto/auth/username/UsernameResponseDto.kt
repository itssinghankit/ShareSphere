package com.example.sharesphere.data.remote.dto.auth.username

import com.example.sharesphere.domain.model.auth.UsernameModel

data class UsernameResponseDto(
    val `data`: Data?=null,
    val message: String?=null,
    val statusCode: Int?=null,
    val success: Boolean?=null
)
data class Data(
    val available: Boolean?=false
)

fun UsernameResponseDto.toUsernameModel(): UsernameModel {
    return UsernameModel(available = data?.available,message=message)
}