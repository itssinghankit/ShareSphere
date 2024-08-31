package com.example.sharesphere.presentation.screens.user.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostItem(modifier: Modifier = Modifier) {
    val isFollowed = false
    var likes by rememberSaveable { mutableIntStateOf(1234) }
    var isLiked by rememberSaveable { mutableStateOf(false) }
    var saveIconChecked by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        PosterDetails(
            avatar = "https://avatars.githubusercontent.com/u/20736539?v=4",
            isFollowed = isFollowed
        )
        val images = listOf(
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo
        )
        ImageCarousel(images = images)

        //likes comment share
        PostBottomBar(
            likes = likes,
            comments = 123,
            isLiked = isLiked,
            onLikeClicked = {
                if (isLiked) {
                    likes--
                } else {
                    likes++
                }
                isLiked = !isLiked
            },
            saveIconChecked = saveIconChecked,
            onSaveClicked = {
                saveIconChecked = !saveIconChecked
            }
        )

    }

}

@Composable
fun PostBottomBar(
    comments: Int,
    likes: Int,
    isLiked: Boolean,
    onLikeClicked: () -> Unit,
    saveIconChecked: Boolean,
    onSaveClicked: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 4.dp, top = 4.dp, bottom = 8.dp),
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
                        onLikeClicked()
                    },
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "like",
                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = likes.toString(),
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
            checked = saveIconChecked,
            onCheckedChange = { onSaveClicked() }

        ) {
            if (saveIconChecked) {
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
}

@Composable
fun PosterDetails(avatar: String, isFollowed: Boolean) {

    var followChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
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
                    text = "Ankit Singh",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "itssinghankit",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
            }
        }

        Row {
            if (!isFollowed) {
                FilterChip(
                    selected = followChecked,
                    label = {
                        Text(
                            text = if (followChecked) "Follow" else "Following",
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
fun ImageCarousel(images: List<Int>) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                shape = RectangleShape,
                elevation = cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Image ${page + 1}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Pager indicator
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
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
