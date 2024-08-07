package com.example.sharesphere.data.remote.dto.avatar

import android.net.Uri
import java.io.File

data class AvatarReqDto(
    val fullName: String,
    val dob: Long,
    val gender: String,
    val bio: String
)
