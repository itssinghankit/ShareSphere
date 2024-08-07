package com.example.sharesphere.presentation.screens.authentication.avatar

import android.net.Uri

sealed class AvatarEvents{
    object OnNextClicked:AvatarEvents()
    object OnSnackBarShown:AvatarEvents()
    object OnNavigationDone:AvatarEvents()
    data class OnImageSelected(val avatar: Uri?):AvatarEvents()
    data class OnBioValueChanged(val bio:String):AvatarEvents()
}
