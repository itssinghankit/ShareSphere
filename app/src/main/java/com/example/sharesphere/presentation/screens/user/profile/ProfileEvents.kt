package com.example.sharesphere.presentation.screens.user.profile

sealed class ProfileEvents {
    object ResetErrorMessage:ProfileEvents()
    data class ShowImagesDialog(val index:Int,val isMyPostDialog:Boolean?):ProfileEvents()
    object OnDismissDialogClicked:ProfileEvents()

}