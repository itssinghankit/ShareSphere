package com.example.sharesphere.presentation.screens.user.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.presentation.ui.theme.orange

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
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

    Column(modifier = modifier) {
        PosterDetails(
            avatar = post.postedBy.avatar,
            isFollowed = post.isFollowed,
            name = post.postedBy.fullName,
            username = post.postedBy.username
        )

        val images = post.postImages
        ImageCarousel(images = images)

        //likes comment share
        PostBottomBar(
            likes = post.likeCount,
            comments = post.commentCount,
            isLiked = post.isLiked,
            onLikeClicked = onLikeClicked,
            saveIconChecked = post.isSaved,
            onSaveClicked = onSaveClicked,
            caption = post.caption,
            postId = post._id,
            isLikeError = isLikeError,
            likedPostId = likedPostId,
            onLikeErrorUpdated = onLikeErrorUpdated,
            isSaveError = isSaveError,
            savedPostId = savedPostId,
            onSaveErrorUpdated = onSaveErrorUpdated

        )

    }

}


@Composable
fun PosterDetails(avatar: String, isFollowed: Boolean, username: String, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
            )

            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = username,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
            }
        }

        Row {
            if (!isFollowed) {
                var followChecked by rememberSaveable { mutableStateOf(false) }
                FilterChip(
                    selected = !followChecked,
                    label = {
                        Text(
                            text = if (followChecked) "Following" else "Follow",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                        )
                    },
                    onClick = { followChecked = !followChecked }
                )
            }


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(modifier: Modifier=Modifier,images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.outlineVariant)
        ) { page ->
//            val painter = rememberAsyncImagePainter(images[page])
//            Image(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(bottom = 8.dp),
//                painter = painter,
//                contentDescription = "Image ${page + 1}",
//                contentScale = ContentScale.Crop
//            )

            AsyncImage(
                model = images[page],
                contentDescription = "Image ${page + 1}",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
            )
        }

        // Pager indicator
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.tertiary else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(4.dp)
                )
            }
        }
    }
}

@Composable
fun PostBottomBar(
    comments: Int,
    likes: Int,
    isLiked: Boolean,
    onLikeClicked: (String) -> Unit,
    saveIconChecked: Boolean,
    onSaveClicked: (String) -> Unit,
    caption: String,
    postId: String,
    isLikeError: Boolean,
    likedPostId: String?,
    onLikeErrorUpdated: () -> Unit,
    isSaveError: Boolean,
    savedPostId: String?,
    onSaveErrorUpdated: () -> Unit
) {
    var isLikedState by rememberSaveable { mutableStateOf(isLiked) }
    var likeCountState by rememberSaveable { mutableIntStateOf(likes) }
    var saveIconCheckedState by rememberSaveable { mutableStateOf(saveIconChecked) }

    LaunchedEffect(isLikeError) {
        if(isLikeError && postId==likedPostId){
            isLikedState = !isLikedState
            likeCountState = if(isLikedState) likeCountState+1 else likeCountState-1
            onLikeErrorUpdated()
        }
    }
    LaunchedEffect(isSaveError) {
        if(isSaveError && postId==savedPostId){
            saveIconCheckedState = !saveIconCheckedState
            onSaveErrorUpdated()
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            if (isLikedState) likeCountState-- else likeCountState++
                            isLikedState = !isLikedState
                            onLikeClicked(postId)
                        },
                    imageVector = if (isLikedState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "like",
                    tint = if (isLikedState) Color.Red else MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = likeCountState.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(27.dp),
                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                    contentDescription = "Chat",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = comments.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    modifier = Modifier
                        .padding(start = 25.dp)
                        .size(27.dp)
                        .graphicsLayer {
                            rotationZ = -25f
                            transformOrigin = TransformOrigin(0f, 1f)  // Bottom start pivot
                        },
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.primary
                )


            }
            //save post
            IconToggleButton(
                checked = saveIconCheckedState,
                onCheckedChange = {
                    saveIconCheckedState=!saveIconCheckedState
                    onSaveClicked(postId) }

            ) {
                if (saveIconCheckedState) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.Bookmark,
                        contentDescription = "bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        if (caption.isNotEmpty() && caption.isNotBlank()) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = caption,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
