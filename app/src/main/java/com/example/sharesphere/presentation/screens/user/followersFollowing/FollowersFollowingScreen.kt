package com.example.sharesphere.presentation.screens.user.followersFollowing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun FollowersFollowingScreen(
    modifier: Modifier = Modifier,
    viewModel: FFViewModel,
    onEvent: (FFEvents) -> Unit,
    onBackPressed:()->Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()
    val tabItems = listOf(
        FFTabItems(
            title = "Followers",
            contentList = uiState.followersData
        ),
        FFTabItems(
            title = "Following",
            contentList = uiState.followingData
        )
    )

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
            ComponentTopBar(text="@${uiState.username?:""}", onStartIconClicked = onBackPressed)
        },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(FFEvents.ResetErrorMessage)
            }
        }) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Loading()
            }
        } else {
            FFTabView(
                modifier = Modifier.padding(padding),
                tabItems = tabItems,
                onFollowClicked = {onEvent(FFEvents.FollowUser(it))},
                onUserClicked = {},
                openFollowersTab=uiState.followersTab?:true
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FFTabView(
    modifier: Modifier = Modifier,
    tabItems: List<FFTabItems>,
    onFollowClicked: (String) -> Unit,
    onUserClicked: (String) -> Unit,
    openFollowersTab:Boolean

) {

    val pagerState = rememberPagerState(initialPage = if(openFollowersTab) 0 else 1, pageCount = { tabItems.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 0.dp)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    text = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState
        ) { page ->
            FFList(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                searchResult = tabItems[page].contentList,
                onFollowClicked = onFollowClicked,
                onUserClicked = onUserClicked
            )
        }
    }

}

@Composable
fun FFList(
    modifier: Modifier = Modifier,
    searchResult: List<UserItemModel>,
    onFollowClicked: (userId: String) -> Unit,
    onUserClicked: (String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxWidth(), contentPadding = PaddingValues(top = 16.dp)) {
        items(searchResult.size) { index ->
            UserListItem(
                modifier = Modifier.padding(bottom = 16.dp),
                avatar = searchResult[index].avatar,
                name = searchResult[index].fullName,
                username = searchResult[index].username,
                userId = searchResult[index]._id,
                isFollowed = searchResult[index].isFollowed,
                onFollowClicked = onFollowClicked,
                onUserClicked = onUserClicked
            )
        }
    }
}

data class FFTabItems(
    val title: String,
    val contentList: List<UserItemModel>
)
