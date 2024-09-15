package com.example.sharesphere.presentation.screens.chat.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.screens.chat.components.ChatItem
import com.example.sharesphere.presentation.screens.user.components.ComponentTopBar
import com.example.sharesphere.presentation.screens.user.search.SearchContent
import com.example.sharesphere.presentation.screens.user.search.SearchEvents
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
    onEvent: (ChatEvents)-> Unit,
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
    var active by rememberSaveable { mutableStateOf(false) }

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
            if (!active) {
                ComponentTopBar(text="Chat", onStartIconClicked = onBackPressed )
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
        Column {
            SearchContent(
                modifier = Modifier.padding(padding),
                searchQuery = viewModel.searchQuery,
                searchResult = uiState.searchResult,
                onSearchQueryChanged = { onEvent(ChatEvents.OnSearchQueryChanged(it)) },
                onFollowClicked = {onEvent(ChatEvents.OnFollowClicked(it))},
                onSearchClicked = {
                    keyboard?.hide()
                },
                isLoading = uiState.isLoading,
                active = active,
                onActiveChanged = {
                    active = it
                    //to remove loading
                    if(!active){
                        onEvent(ChatEvents.OnSearchActiveClosed)
                    }
                },
                onUserClicked={}
            )

            LazyColumn(modifier=Modifier.padding(top = 4.dp),contentPadding = PaddingValues(top = 8.dp)) {
                items(20){
                    ChatItem(name = "Ankit Singh", lastMessage = "Aur bhai kya haal chal sab badhiyahaal chal sab badhiyahaal chal sab badhiya ", time = "12:00 AM", messageLeft = 10)
                }
            }
        }


    }

}