package com.example.sharesphere.presentation.screens.chat.message

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import com.example.sharesphere.presentation.components.ComponentDayNightTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.chat.components.ChatMessageItem
import com.example.sharesphere.presentation.screens.chat.components.ChatTopBar
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessageScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatMessageViewModel,
    onEvent: (ChatMessageEvents) -> Unit,
    onBackPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()

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
        topBar = {
            uiState.apply {
                ChatTopBar(
                    name = fullName ?: "",
                    username = username ?: "",
                    avatar = avatar ?: "",
                    onBackBtnClicked = onBackPressed
                )
            }
        },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(ChatMessageEvents.ResetErrorMessage)
            }
        }) { padding ->
        ChatMessageContent(
            modifier.padding(padding),
            messageList = uiState.messages,
            myMessage = viewModel.myMessage,
            onMessageValueChange = { onEvent(ChatMessageEvents.OnMessageValueChanged(it)) },
            onMessageSendButtonClicked = {onEvent(ChatMessageEvents.OnMessageSendButtonClicked)},
            isChatLoading = uiState.isLoading)
    }
}

@Composable
fun ChatMessageContent(
    modifier: Modifier = Modifier,
    messageList: List<GetAllMessagesModel>,
    myMessage: String,
    onMessageValueChange: (String) -> Unit,
    onMessageSendButtonClicked: () -> Unit,
    isChatLoading:Boolean,
) {

    if (isChatLoading) {
        Box(modifier=modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
            Loading()
        }
    } else {

        Column(modifier = modifier) {
            Column(modifier = Modifier.weight(1f)) {
                ChatMessages(list = messageList)
            }
            ComponentDayNightTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                value = myMessage,
                onValueChange = onMessageValueChange,
                leadingIconImageVector = Icons.AutoMirrored.Outlined.Message,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(onSend = {
                    if(myMessage.isNotEmpty() && myMessage.isNotBlank()){
                        onMessageSendButtonClicked()
                    }
                }),
                shape = RoundedCornerShape(32.dp)
            )
        }
    }

}

@Composable
fun ChatMessages(modifier: Modifier = Modifier, list: List<GetAllMessagesModel>) {
    val dataSize = list.size
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(bottom = 8.dp),
        reverseLayout = true
    ) {
        items(dataSize) { index ->

            if (index != dataSize - 1 && list[index].senderId == list[index + 1].senderId) {
                list[index].apply {
                    ChatMessageItem(
                        message = content,
                        isOwner = isOwner,
                        isSameUserMsg = true,
                        avatar = senderAvatar,
                        time = updatedAt,
                        name = fullName
                    )
                }

            } else {
                list[index].run {
                    ChatMessageItem(
                        message = content,
                        isOwner = isOwner,
                        isSameUserMsg = false,
                        avatar = senderAvatar,
                        time = updatedAt,
                        name = fullName
                    )
                }
            }

        }
    }
}
