package com.example.sharesphere.presentation.screens.chat.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.domain.model.user.common.UserItemModel
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.chat.components.ChatItem
import com.example.sharesphere.presentation.screens.user.components.ComponentTopBar
import com.example.sharesphere.presentation.screens.user.search.SearchContent
import com.example.sharesphere.util.NetworkMonitor
import formatDateTimeRelative
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onEvent: (ChatEvents) -> Unit,
    onBackPressed: () -> Unit,
    onChatClicked: (chatId: String, fullName: String, username: String, avatar: String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()
    var active by rememberSaveable { mutableStateOf(false) }

    //Showing snackBar for Errors
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    DisposableEffect(uiState.navigateToChatMsgScreen) {
        if (uiState.navigateToChatMsgScreen) {
            onChatClicked(
                uiState.newChatId!!,
                uiState.newChatFullName!!,
                uiState.newChatUserName!!,
                uiState.newChatAvatar!!
            )
        }
        onDispose {
            onEvent(ChatEvents.OnNavigationDone)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (!active) {
                ComponentTopBar(text = "Chat", onStartIconClicked = onBackPressed)
            }
        },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(ChatEvents.ResetErrorMessage)
            }
        }) { padding ->
        ChatContent(
            modifier = Modifier.padding(padding),
            searchQuery = viewModel.searchQuery,
            searchResult = uiState.searchResult,
            onSearchQueryChanged = { onEvent(ChatEvents.OnSearchQueryChanged(it)) },
            onFollowClicked = { onEvent(ChatEvents.OnFollowClicked(it)) },
            onSearchClicked = {
                keyboard?.hide()
            },
            isLoading = uiState.isLoading,
            active = active,
            onActiveChanged = {
                active = it
                //to remove loading
                if (!active) {
                    onEvent(ChatEvents.OnSearchActiveClosed)
                }
            },
            onUserClicked = { onEvent(ChatEvents.OnSearchChatClicked(it)) },
            chat = uiState.chats,
            userId = uiState.userId!!,
            isChatLoading = uiState.isChatLoading,
            onChatClicked = { chatId, fullName, username, avatar ->
                onChatClicked(
                    chatId,
                    fullName,
                    username,
                    avatar
                )
            }
        )
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatContent(
    modifier: Modifier,
    isLoading: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    searchResult: List<UserItemModel>?,
    onFollowClicked: (userId: String) -> Unit,
    onSearchClicked: (String) -> Unit,
    active: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    onUserClicked: (String) -> Unit,
    chat: List<Chat>,
    userId: String,
    isChatLoading: Boolean,
    onChatClicked: (chatId: String, fullName: String, username: String, avatar: String) -> Unit
) {

    Column {
        SearchContent(
            modifier = modifier,
            searchQuery = searchQuery,
            searchResult = searchResult,
            onSearchQueryChanged = onSearchQueryChanged,
            onFollowClicked = onFollowClicked,
            onSearchClicked = onSearchClicked,
            isLoading = isLoading,
            active = active,
            onActiveChanged = onActiveChanged,
            onUserClicked = onUserClicked
        )

        if (isChatLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                Loading()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 4.dp),
                contentPadding = PaddingValues(top = 8.dp)
            ) {

                items(chat.size) { index ->
                    var name = ""
                    var avatar = ""
                    var username = ""
                    chat[index].participants.forEachIndexed { _, participant ->

                        if (participant._id != userId) {
                            name = participant.fullName
                            avatar = participant.avatar
                            username = participant.username
                        }
                    }
                    ChatItem(
                        name = name,
                        avatar = avatar,
                        lastMessage = chat[index].lastMessage?.content ?: "",
                        time = chat[index].lastMessage?.let { formatDateTimeRelative(it.updatedAt) }
                            ?: "",
                        messageLeft = 0,
                        onChatClicked = { onChatClicked(chat[index]._id, name, username, avatar) }
                    )
                }
            }
        }
    }

}