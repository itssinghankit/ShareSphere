package com.example.sharesphere.presentation.screens.user.home

sealed class HomeEvents {
    object ResetErrorMessage : HomeEvents()
    data class LikePost(val postId: String) : HomeEvents()
    object LikeErrorUpdatedSuccessfully : HomeEvents()
    data class SavePost(val postId: String) : HomeEvents()
    data class OnFollowClicked(val userId: String) : HomeEvents()
    object SaveErrorUpdatedSuccessfully : HomeEvents()
    data class ShowComments(val postId: String) : HomeEvents()
    object OnCommentsBottomSheetDismissed : HomeEvents()
    object AddComment : HomeEvents()
    data class OnCommentValueChange(val comment: String) : HomeEvents()
}