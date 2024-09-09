package com.example.sharesphere.presentation.screens.user.search

sealed class SearchEvents {
    object ResetErrorMessage: SearchEvents()
    data class OnSearchQueryChanged(val searchQuery: String): SearchEvents()
    data class OnFollowClicked(val userId: String): SearchEvents()
    object OnSearchActiveClosed:SearchEvents()
}