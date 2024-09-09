package com.example.sharesphere.presentation.screens.user.followersFollowing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.user.followersFollowing.UserItemModel
import com.example.sharesphere.presentation.screens.user.components.UserListItem
import com.example.sharesphere.presentation.ui.theme.ShareSphereTheme
import kotlinx.coroutines.launch

@PreviewLightDark
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun followersPre() {
    ShareSphereTheme {
        Surface() {
            FollowersFollowingScreen()
        }
    }
}

@Composable
fun FollowersFollowingScreen() {
    val onFollowClicked = { it: String -> }
    val onUserClicked = { it: String -> }
    val tabItems = listOf(
        FFTabItems(
            title = "Followers",
            contentList = emptyList()
        ),
        FFTabItems(
            title = "Following",
            contentList = emptyList()
        )
    )
    Column {
        FFTabView(
            tabItems = tabItems,
            onFollowClicked = onFollowClicked,
            onUserClicked = onUserClicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FFTabView(
    modifier: Modifier = Modifier,
    tabItems: List<FFTabItems>,
    onFollowClicked: (String) -> Unit,
    onUserClicked: (String) -> Unit

) {

    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
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
            state = pagerState,
        ) { page ->

            FFList(
                modifier = Modifier.padding(horizontal = 16.dp),
                searchResult = tabItems[page].contentList,
                onFollowClicked = onFollowClicked,
                onUserClicked=onUserClicked
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
