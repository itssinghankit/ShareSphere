package com.example.sharesphere.presentation.screens.chat.chat

sealed class ChatEvents {
    object ResetErrorMessage:ChatEvents()
    data class OnSearchQueryChanged(val searchQuery: String): ChatEvents()
    data class OnFollowClicked(val userId: String): ChatEvents()
    object OnSearchActiveClosed: ChatEvents()
    data class OnSearchChatClicked(val receiverId:String):ChatEvents()
    object OnNavigationDone:ChatEvents()
}