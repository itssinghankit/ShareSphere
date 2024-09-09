package com.example.sharesphere.presentation.screens.user.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentDayNightTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.user.components.ImageCarousel
import com.example.sharesphere.presentation.screens.user.search.SearchTopBar
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import kotlinx.coroutines.launch


@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    viewModel: PostViewModel,
    onEvent: (PostEvents) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle()
    val keyboard = LocalSoftwareKeyboardController.current
    val multipleImageSelectorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if (uris.size > 10) {
                onEvent(PostEvents.SetError(UiText.StringResource(R.string.validate_images_error)))
            } else {
                onEvent(PostEvents.OnImagesSelected(uris))
            }
        }
    )


    //Showing snackBar for Errors
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            keyboard?.hide()
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            PostTopBar(onPostClicked = {
                keyboard?.hide()
                onEvent(PostEvents.OnPostClicked)})
        },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(PostEvents.ResetErrorMessage)
            }
        }) { padding ->

        PostContent(
            modifier = Modifier.padding(padding),
            images = uiState.postImages,
            onSelectImagesClicked = {
                keyboard?.hide()
                multipleImageSelectorLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onEditImagesClicked = {
                keyboard?.hide()
                onEvent(PostEvents.ResetPost)
                multipleImageSelectorLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onCancelClicked = {
                keyboard?.hide()
                onEvent(PostEvents.ResetPost)
            },
            caption = viewModel.caption,
            onCaptionChanged = { onEvent(PostEvents.OnCaptionTextChanged(it)) },
            isCaptionError = uiState.isCaptionError,
            captionError = uiState.captionError?.asString() ?: "",
            isLoading = uiState.isLoading
        )
    }


}

@Composable
fun PostTopBar(modifier: Modifier = Modifier, onPostClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Create Post",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.cirka_bold))
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onPostClicked()
                    },
                text = "Post",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary,
                fontFamily = FontFamily(Font(R.font.cirka_bold))
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp)
        )
    }

}

@Composable
fun PostContent(
    modifier: Modifier,
    images: List<Uri>,
    onSelectImagesClicked: () -> Unit,
    onEditImagesClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    caption: String,
    onCaptionChanged: (String) -> Unit,
    isCaptionError: Boolean,
    captionError: String,
    isLoading: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        PostImages(
            modifier = Modifier
                .padding(16.dp),
            images = images
        )
        PostCaption(
            modifier = Modifier.padding(horizontal = 16.dp),
            caption = caption,
            onCaptionChanged = onCaptionChanged,
            showError = isCaptionError,
            captionError = captionError
        )
        PostActions(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 64.dp),
            isLoading = isLoading,
            onSelectImagesClicked = onSelectImagesClicked,
            onEditImagesClicked = onEditImagesClicked,
            onCancelClicked = onCancelClicked,
            showEditCancelButton = images.isNotEmpty()
        )
    }

}

@Composable
fun PostActions(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    showEditCancelButton: Boolean,
    onSelectImagesClicked: () -> Unit,
    onEditImagesClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {

    if (isLoading) {
        Loading()
    } else {
        if (showEditCancelButton) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ComponentButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    text = "Edit Images"
                ) {
                    onEditImagesClicked()
                }
                ComponentButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = "Cancel"
                ) {
                    onCancelClicked()
                }
            }
        } else {
            ComponentButton(
                modifier = modifier,
                text = "Add Images"
            ) {
                onSelectImagesClicked()
            }
        }
    }

}

@Composable
fun PostCaption(
    modifier: Modifier = Modifier,
    caption: String,
    onCaptionChanged: (String) -> Unit,
    showError: Boolean,
    captionError: String,
) {
    ComponentDayNightTextField(
        modifier = modifier,
        value = caption,
        onValueChange = { onCaptionChanged(it)},
        leadingIconImageVector = Icons.Outlined.Subtitles,
        label = "Caption",
        maxLines = 3,
        singleLine = false,
        showError = showError,
        errorMessage = captionError,
    )

}

@Composable
fun PostImages(
    modifier: Modifier,
    images: List<Uri>
) {
    ImageCarousel(modifier = modifier, images.map { it.toString() })
}