package com.example.sharesphere.presentation.screens.user.home

import com.example.sharesphere.presentation.screens.user.search.SearchEvents

sealed class HomeEvents {
    object ResetErrorMessage:HomeEvents()
    data class LikePost(val postId:String):HomeEvents()
    object LikeErrorUpdatedSuccessfully:HomeEvents()
    data class SavePost(val postId:String):HomeEvents()
    data class OnFollowClicked(val userId: String): HomeEvents()
    object SaveErrorUpdatedSuccessfully:HomeEvents()
}