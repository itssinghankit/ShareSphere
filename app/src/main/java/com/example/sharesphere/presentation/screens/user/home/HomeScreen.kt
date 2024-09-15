package com.example.sharesphere.presentation.screens.user.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.domain.model.user.common.ShowCommentsModel
import com.example.sharesphere.presentation.components.ComponentDayNightTextField
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.user.components.PostListContent
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onEvent: (HomeEvents) -> Unit,
    navigateToAccountScreen: () -> Unit,
    navigateToViewProfileScreen: (userId: String) -> Unit,
    navigateToChatScreens: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()
    val posts = viewModel.posts.collectAsLazyPagingItems()
    var showCommentsBottomSheet by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current

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
        Box {
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
                onSaveClicked = { onEvent(HomeEvents.SavePost(it)) },
                onSaveErrorUpdated = { onEvent(HomeEvents.SaveErrorUpdatedSuccessfully) },
                onFollowClicked = { onEvent(HomeEvents.OnFollowClicked(it)) },
                showComments = { postId ->
                    onEvent(HomeEvents.ShowComments(postId))
                    showCommentsBottomSheet = true
                },
                onAccountClicked = navigateToAccountScreen,
                onProfileClicked={navigateToViewProfileScreen(it)},
                onChatClicked=navigateToChatScreens

            )
            if (showCommentsBottomSheet) {
                CommentsModelSheet(
                    onDismissRequest = {
                        onEvent(HomeEvents.OnCommentsBottomSheetDismissed)
                        showCommentsBottomSheet = false
                    },
                    commentsList = uiState.commentsData,
                    isCommentsLoading = uiState.isCommentsLoading,
                    addComment={
                        keyboard?.hide()
                        onEvent(HomeEvents.AddComment)},
                    comment = viewModel.comment,
                    onCommentValueChange = {onEvent(HomeEvents.OnCommentValueChange(it))}

                )
            }
        }

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
    onSaveErrorUpdated: () -> Unit,
    onFollowClicked: (String) -> Unit,
    showComments: (String) -> Unit,
    onAccountClicked:()->Unit,
    onProfileClicked: (String) -> Unit,
    onChatClicked:()->Unit
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
            Row {
                IconButton(onClick = {onAccountClicked()}) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Account",
                        tint = MaterialTheme.colorScheme.primary

                    )
                }
                IconButton(
                    modifier=Modifier.padding(end = 8.dp),onClick = {onChatClicked()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = "chat",
                        tint = MaterialTheme.colorScheme.primary

                    )
                }
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
            onSaveErrorUpdated = onSaveErrorUpdated,
            onFollowClicked = onFollowClicked,
            showComments = showComments,
            onProfileClicked=onProfileClicked
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsModelSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    commentsList: List<ShowCommentsModel>,
    isCommentsLoading: Boolean,
    addComment:()->Unit,
    comment:String,
    onCommentValueChange:(String)->Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {

        Column(Modifier.fillMaxHeight()) {
            Column(Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Comments",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
                HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 16.dp))
                if(commentsList.isEmpty()){
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 32.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "No Comments found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.gilroy_regular))
                    )
                }else{
                    CommentsList(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        comments = commentsList
                    )
                }

            }
            if (!isCommentsLoading) {
                ComponentDayNightTextField(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 54.dp)
                        .align(Alignment.CenterHorizontally),
                    label = "Add comment",
                    value = comment,
                    onValueChange = { onCommentValueChange(it) },
                    leadingIconImageVector = Icons.Outlined.Subtitles,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(onSend = {addComment()})
                )
            }else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Loading()
                }
            }
        }
    }

}

@Composable
fun CommentsList(modifier: Modifier = Modifier, comments: List<ShowCommentsModel>) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(comments.size) { index ->
            CommentItem(content = comments[index], onProfileClicked = {})
        }
    }
}

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    content: ShowCommentsModel,
    onProfileClicked: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = content.avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClicked(content._id) },
                contentScale = ContentScale.Crop,
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
            )

            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = content.username,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = content.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }
        }

    }

}

