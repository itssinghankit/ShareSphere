package com.example.sharesphere.presentation.screens.user.post

import android.net.Uri
import com.example.sharesphere.util.UiText

sealed class PostEvents {
    object ResetErrorMessage:PostEvents()
    data class OnCaptionTextChanged(val caption:String):PostEvents()
    object ResetPost:PostEvents()
    data class OnImagesSelected(val uris: List<Uri>): PostEvents()
    data class SetError(val errorMessage:UiText):PostEvents()
    object OnPostClicked:PostEvents()
}