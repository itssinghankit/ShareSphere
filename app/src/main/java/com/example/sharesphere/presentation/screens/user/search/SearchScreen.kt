package com.example.sharesphere.presentation.screens.user.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.user.common.UserItemModel
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.user.components.ComponentTopBar
import com.example.sharesphere.presentation.screens.user.components.UserListItem
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onEvent: (SearchEvents) -> Unit
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
                ComponentTopBar(text="Search User")
            }

        },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(SearchEvents.ResetErrorMessage)
            }
        }) { padding ->
        SearchContent(
            modifier = Modifier.padding(padding),
            searchQuery = viewModel.searchQuery,
            searchResult = uiState.searchResult,
            onSearchQueryChanged = { onEvent(SearchEvents.OnSearchQueryChanged(it)) },
            onFollowClicked = {onEvent(SearchEvents.OnFollowClicked(it))},
            onSearchClicked = {
                keyboard?.hide()
            },
            isLoading = uiState.isLoading,
            active = active,
            onActiveChanged = {
                active = it
                //to remove loading
                if(!active){
                    onEvent(SearchEvents.OnSearchActiveClosed)
                }
            },
            onUserClicked={}
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
    modifier: Modifier,
    isLoading: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    searchResult: List<UserItemModel>?,
    onFollowClicked: (userId: String) -> Unit,
    onSearchClicked: (String) -> Unit,
    active: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    onUserClicked:  (String) -> Unit
) {

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (active) 0.dp else 16.dp,
                end = if (active) 0.dp else 16.dp,
                top = if (!active) 8.dp else 0.dp
            ),
        query = searchQuery,
        onQueryChange = onSearchQueryChanged,
        onSearch = onSearchClicked,
        active = active,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        colors = SearchBarDefaults.colors(
            containerColor = if(!active)MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.background,
            dividerColor = MaterialTheme.colorScheme.secondary
        ),
        onActiveChange = {
            onActiveChanged(it)
        },
        shape = RectangleShape,
        placeholder = {
            if (!active) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Search Name/Username",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }
        }
    ) {
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Loading()
            }
        } else if (searchResult != null) {
            if (searchResult.isEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "No user found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            } else {
                SearchList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    searchResult = searchResult,
                    onFollowClicked = onFollowClicked,
                    onUserClicked=onUserClicked
                )

            }
        }

    }
}

@Composable
fun SearchList(
    modifier: Modifier = Modifier,
    searchResult: List<UserItemModel>,
    onFollowClicked: (userId: String) -> Unit,
    onUserClicked: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxWidth(), contentPadding = PaddingValues(top = 16.dp),) {
        items(searchResult.size) { index ->
            UserListItem(
                modifier = Modifier.padding(bottom = 16.dp,),
                avatar = searchResult[index].avatar,
                name = searchResult[index].fullName,
                username = searchResult[index].username,
                userId = searchResult[index]._id,
                isFollowed = searchResult[index].isFollowed,
                onFollowClicked = onFollowClicked,
                onUserClicked=onUserClicked
            )
        }
    }
}




