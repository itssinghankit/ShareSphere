package com.example.sharesphere.presentation.screens.chat.chat

import com.example.sharesphere.presentation.screens.user.search.SearchEvents

sealed class ChatEvents {
    object ResetErrorMessage:ChatEvents()
    data class OnSearchQueryChanged(val searchQuery: String): ChatEvents()
    data class OnFollowClicked(val userId: String): ChatEvents()
    object OnSearchActiveClosed: ChatEvents()
}