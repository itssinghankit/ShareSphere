package com.example.sharesphere.presentation.screens.authentication.avatar

import android.net.Uri
import com.example.sharesphere.util.UiText

data class AvatarStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val avatar: Uri? = null,
    val isBioError: Boolean = false,
    val navigate:Boolean=false,
)
