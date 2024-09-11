package com.example.sharesphere.presentation.screens.user.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.sharesphere.data.commonDto.user.home.post.Post
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListContent(
    items: LazyPagingItems<Post>,
    onLikeClicked: (String) -> Unit,
    onSaveClicked: (String) -> Unit,
    isLikeError: Boolean,
    likedPostId: String?,
    onLikeErrorUpdated: () -> Unit,
    isSaveError: Boolean,
    savedPostId: String?,
    onSaveErrorUpdated: () -> Unit,
    onFollowClicked: (String) -> Unit,
    showComments:(String)->Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(count = items.itemCount, key = { index -> items[index]?._id?: index.toString() }
        ) { index ->
            val item = items[index]
            item?.let {
                PostItem(
                    post = item,
                    onLikeClicked = onLikeClicked,
                    likedPostId = likedPostId,
                    onSaveClicked = onSaveClicked,
                    isLikeError = isLikeError,
                    onLikeErrorUpdated = onLikeErrorUpdated,
                    isSaveError = isSaveError,
                    savedPostId = savedPostId,
                    onSaveErrorUpdated = onSaveErrorUpdated,
                    onFollowClicked = onFollowClicked,
                    showComments=showComments
                )
            }
        }

    }
}