package com.example.sharesphere.presentation.screens.user.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.domain.model.user.profile.MyPostModel
import com.example.sharesphere.domain.model.user.profile.SavedPostModel
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.user.components.PostItem
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    onEvent: (ProfileEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


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
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(ProfileEvents.ResetErrorMessage)
            }
        }) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Loading()
            }
        } else {
            uiState.accountDetails?.let {
                Box(contentAlignment = Alignment.Center) {
                    ProfileContent(
                        modifier = Modifier.padding(padding),
                        name = it.fullName,
                        username = it.username,
                        bio = it.bio,
                        avatar = it.avatar,
                        followers = it.followers,
                        following = it.following,
                        myPostsList = uiState.myPostsData,
                        savedPostList = uiState.savedPostsData,
                        onGridImageClicked = { index, isMyPostDialog ->
                            onEvent(ProfileEvents.ShowImagesDialog(index, isMyPostDialog))
                        }
                    )
                    if (uiState.showDialog) {
                        ViewImagesDialog(
                            onDismissClicked = { onEvent(ProfileEvents.OnDismissDialogClicked) },
                            post = uiState.dialogData!!,
                            onLikeClicked = {},
                            onSaveClicked = {},
                            isLikeError = false,
                            likedPostId = null,
                            onLikeErrorUpdated = {},
                            isSaveError = false,
                            savedPostId = null,
                            onSaveErrorUpdated = {}
                        )
                    }
                }

            }
        }
    }


}


@Composable
fun ProfileContent(
    modifier: Modifier,
    name: String,
    username: String,
    bio: String,
    avatar: String,
    followers: Int,
    following: Int,
    myPostsList: List<MyPostModel>,
    savedPostList: List<SavedPostModel>,
    onGridImageClicked: (index: Int, isMyPostDialog: Boolean?) -> Unit
) {

    val owner = true
    val onEditClicked = {}
    val onFollowClicked = {}
    val isFollowed = false
    val onShareClicked = {}
    val posts = myPostsList.size
    val onFollowersClicked = {}
    val onFollowingClicked = {}
    val tabItems = listOf(
        ProfileTabItems(
            filledIcon = Icons.Filled.PhotoLibrary,
            outlinedIcon = Icons.Outlined.PhotoLibrary,
            contentList = myPostsList.map { it.postImages[0] },
            onItemClick = {
                onGridImageClicked(it, true)
            }
        ),
        ProfileTabItems(
            filledIcon = Icons.Filled.Bookmarks,
            outlinedIcon = Icons.Outlined.Bookmarks,
            contentList = savedPostList.map { it.postDetails.postImages[0] },
            onItemClick = {
                onGridImageClicked(it, false)
            }
        )
    )
//    var currentDelta by remember { mutableStateOf(0.dp) }
//    val scrollState = rememberScrollState()
//
//    val coroutineScope = rememberCoroutineScope()
//
//    val nestedScrollConnection= remember {
//        object :NestedScrollConnection{
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                val delta = available.y
//                val newOffset = scrollState.value - delta.toInt()
//                return if (newOffset in 0..100) {
//                    coroutineScope.launch {
//                        scrollState.scrollTo(newOffset)
//                    }
//
//                    // Consume the scroll
//                    available
////                    return if (delta < 0) {
////                        coroutineScope.launch {
////                            scrollState.scrollBy(-delta)
////                        }
////                        Offset.Zero
//                } else {
//                    // Don't consume the scroll
//                    Offset.Zero
//                }
//            }
//
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopBar(owner, username)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        AvatarSection(avatar, name, bio)
        ProfileInfo(
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
            followers = followers,
            following = following,
            posts = posts,
            onFollowersClicked = onFollowersClicked,
            onFollowingClicked = onFollowingClicked
        )
        ButtonSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            isFollowed = isFollowed,
            onEditClicked = onEditClicked,
            onFollowClicked = onFollowClicked,
            onShareClicked = onShareClicked,
            owner = owner
        )
        TabView(tabItems = tabItems)
    }

}



@Composable
fun ProfileInfo(
    modifier: Modifier,
    followers: Int,
    following: Int,
    posts: Int,
    onFollowersClicked: () -> Unit,
    onFollowingClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "${posts}",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
                Text(
                    text = "Posts",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onFollowersClicked() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "${followers}",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
                Text(
                    text = "Followers",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onFollowingClicked() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "${following}",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
                Text(
                    text = "Following",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }

        }

    }

}

@Composable
fun ButtonSection(
    isFollowed: Boolean,
    owner: Boolean,
    onEditClicked: () -> Unit,
    onFollowClicked: () -> Unit,
    onShareClicked: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if (owner) {
            FilterChip(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                selected = true,
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Edit Profile",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                        textAlign = TextAlign.Center
                    )
                },
                onClick = onEditClicked
            )

        } else {
            FilterChip(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = if (isFollowed) Color.Unspecified else MaterialTheme.colorScheme.tertiary),
                selected = !isFollowed,
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (isFollowed) "Followed" else "Follow",
                        color = if (isFollowed) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.Black else Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                        textAlign = TextAlign.Center

                    )
                },
                onClick = onFollowClicked
            )

        }
        FilterChip(
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp),
            selected = true,
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Share Profile",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                    textAlign = TextAlign.Center

                )
            },
            onClick = onShareClicked
        )

    }
}

@Composable
fun AvatarSection(avatar: String, name: String, bio: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = avatar,
            contentDescription = "avatar",
            modifier = Modifier
                .padding(start = 16.dp, top = 36.dp)
                .size(100.dp)
                .border(4.dp, MaterialTheme.colorScheme.outline, CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 48.dp),
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily(Font(R.font.cirka_bold))
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp),
                text = bio,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.gilroy_regular))
            )
        }
    }
}

@Composable
fun TopBar(owner: Boolean, userId: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = "@${userId}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.cirka_bold))
        )
        if (owner) {
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.PhotoAlbum,
                    contentDescription = "saved",
                    tint = MaterialTheme.colorScheme.primary

                )
            }
        } else {
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Forum,
                    contentDescription = "saved",
                    tint = MaterialTheme.colorScheme.primary

                )
            }
        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabView(
    modifier: Modifier = Modifier,
    tabItems: List<ProfileTabItems>,
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
                    icon = {
                        if (pagerState.currentPage == index) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = item.filledIcon,
                                contentDescription = "photos",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = item.outlinedIcon,
                                contentDescription = "photos",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
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

            ProfileGridImages(
                imagesList = tabItems[page].contentList,
                onItemClick = tabItems[page].onItemClick
            )

        }
    }

}

@Composable
fun ProfileGridImages(
    onItemClick: (index: Int) -> Unit,
    imagesList: List<Any>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(imagesList.size) { index ->
            ProfileGridItem(
                index = index,
                image = imagesList[index].toString(),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ProfileGridItem(index: Int, image: String, onItemClick: (index: Int) -> Unit) {
    AsyncImage(
        model = image,
        contentDescription = "image",
        modifier = Modifier
            .padding(1.dp)
            .aspectRatio(1f)
            .clickable {

                onItemClick(index)
            },
        contentScale = ContentScale.Crop,
        placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
    )

//    Card(
//        modifier = Modifier
//            .padding(4.dp)
//            .fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .aspectRatio(1f)
//                .background(Color.LightGray),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "Item $index")
//        }
//    }
}

@Composable
fun ViewImagesDialog(
    onDismissClicked: () -> Unit,
    post: Post,
    onLikeClicked: (String) -> Unit,
    onSaveClicked: (String) -> Unit,
    isLikeError: Boolean,
    likedPostId: String?,
    onLikeErrorUpdated: () -> Unit,
    isSaveError: Boolean,
    savedPostId: String?,
    onSaveErrorUpdated: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissClicked,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        PostItem(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.medium
                )
                .border(1.5.dp,MaterialTheme.colorScheme.outline,shape = MaterialTheme.shapes.medium)
            ,
            post = post,
            onLikeClicked = onLikeClicked,
            onSaveClicked = onSaveClicked,
            isLikeError = isLikeError,
            likedPostId = likedPostId,
            onLikeErrorUpdated = onLikeErrorUpdated,
            isSaveError = isSaveError,
            savedPostId = savedPostId,
            onSaveErrorUpdated = onSaveErrorUpdated
        )
    }

}

data class ProfileTabItems(
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val contentList: List<String>,
    val onItemClick: (index: Int) -> Unit
)

//@PreviewLightDark
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewProfileScreen() {
//    ShareSphereTheme {
//        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            ProfileContent(modifier = Modifier.padding(1))
//
//        }
//    }
//}