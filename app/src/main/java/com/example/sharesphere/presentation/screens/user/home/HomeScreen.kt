package com.example.sharesphere.presentation.screens.user.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.screens.user.components.PostListContent
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onEvent: (HomeEvents) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val posts = viewModel.posts.collectAsLazyPagingItems()


    //Showing snackBar for Errors
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(HomeEvents.ResetErrorMessage)
            }
        }) { padding ->

        HomeContent(
            modifier = Modifier.padding(padding),
            posts,
            onLikeClicked = { postId ->
                onEvent(HomeEvents.LikePost(postId))
            },
            isLikeError = uiState.isLikeError,
            likedPostId = uiState.likedPostId,
            onLikeErrorUpdated = { onEvent(HomeEvents.LikeErrorUpdatedSuccessfully) },
            isSaveError = uiState.isSaveError,
            savedPostId = uiState.savedPostId,
            onSaveClicked = {onEvent(HomeEvents.SavePost(it))},
            onSaveErrorUpdated = { onEvent(HomeEvents.SaveErrorUpdatedSuccessfully) }
        )

    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    posts: LazyPagingItems<Post>,
    onLikeClicked: (String) -> Unit,
    onSaveClicked: (String) -> Unit,
    isLikeError: Boolean,
    likedPostId: String?,
    onLikeErrorUpdated: () -> Unit,
    isSaveError: Boolean,
    savedPostId: String?,
    onSaveErrorUpdated: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "ShareSphere",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.cirka_bold))
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Setting",
                    tint = MaterialTheme.colorScheme.primary

                )
            }

        }
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 4.dp))

        PostListContent(
            posts,
            onLikeClicked = onLikeClicked,
            onSaveClicked = onSaveClicked,
            isLikeError = isLikeError,
            onLikeErrorUpdated = onLikeErrorUpdated,
            likedPostId = likedPostId,
            isSaveError = isSaveError,
            savedPostId = savedPostId,
            onSaveErrorUpdated = onSaveErrorUpdated
        )

    }

}
