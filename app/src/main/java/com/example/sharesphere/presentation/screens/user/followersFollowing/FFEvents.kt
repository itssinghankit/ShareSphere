package com.example.sharesphere.presentation.screens.user.followersFollowing

sealed class FFEvents {
    object ResetErrorMessage:FFEvents()
    data class FollowUser(val accountId:String):FFEvents()
}