package com.example.sharesphere.presentation.screens.user.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.ui.theme.ShareSphereTheme
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
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(ProfileEvents.ResetErrorMessage)
            }
        }) { padding ->
        Row(modifier = Modifier.padding(padding)) { }
        ProfileContent(

        )
    }


}


@Composable
fun ProfileContent() {
    val owner = false
    val userId = "itssingankit"
    val avatar = "https://www.w3schools.com/w3images/avatar6.png"
    val name = "Ankit Singh"
    val bio = "In the world of darkness, look up at the stars✨✨"
    val onEditClicked = {}
    val onFollowClicked = {}
    val isFollowed = false
    val onShareClicked = {}

    Column(modifier = Modifier.fillMaxWidth()) {
        TopBar(owner, userId)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        AvatarSection(avatar, name, bio)
        ButtonSection(
            isFollowed = isFollowed,
            onEditClicked = onEditClicked,
            onFollowClicked = onFollowClicked,
            onShareClicked = onShareClicked,
            owner = owner
        )
    }
}

@Composable
fun ButtonSection(
    isFollowed: Boolean,
    owner: Boolean,
    onEditClicked: () -> Unit,
    onFollowClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if (owner) {
            FilterChip(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
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
                    .padding(end = 4.dp),
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = if (isFollowed)  Color.Unspecified else  MaterialTheme.colorScheme.tertiary ),
                selected = !isFollowed,
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (isFollowed) "Followed" else "Follow",
                        color = if (isFollowed) MaterialTheme.colorScheme.primary else if(isSystemInDarkTheme()) Color.Black else Color.White,
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
                .padding(start = 4.dp),
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
                .padding(start = 16.dp, top = 40.dp)
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

@PreviewLightDark
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProfileScreen() {
    ShareSphereTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileContent()

        }
    }
}