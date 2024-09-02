package com.example.sharesphere.presentation.screens.user.home

sealed class HomeEvents {
    object ResetErrorMessage:HomeEvents()
    data class LikePost(val postId:String):HomeEvents()
    object LikeErrorUpdatedSuccessfully:HomeEvents()
    data class SavePost(val postId:String):HomeEvents()
    object SaveErrorUpdatedSuccessfully:HomeEvents()
}