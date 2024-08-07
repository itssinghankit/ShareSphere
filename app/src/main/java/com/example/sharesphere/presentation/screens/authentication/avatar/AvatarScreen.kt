package com.example.sharesphere.presentation.screens.authentication.avatar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopBar
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.presentation.ui.theme.Black70
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch


@Composable
fun AvatarScreen(
    viewModel: AvatarViewModel,
    onEvent: (AvatarEvents) -> Unit,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState = viewModel.textFieldStates

    val isError by derivedStateOf {
        textFieldState.bio.isEmpty() || uiState.isBioError || uiState.avatar == null
    }


    //showing snackBar
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

//    for navigation
    DisposableEffect(uiState.navigate) {
        if (uiState.navigate) {
            navigateToHomeScreen()
        }
        onDispose {
            onEvent(AvatarEvents.OnNavigationDone)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = {
                    onBackClicked()
                },
                mainTxt = "add your profile photo and bio",
                supportingTxt = stringResource(id = R.string.sharesphere_application)
            )
        },
        bottomBar = {
            when {
                !networkState.isAvailable() -> {
                    focusManager.clearFocus(true)
                    keyboard?.hide()
                    ConnectionLostScreen()
                }

                uiState.isLoading -> {
                    Loading(color = Black05)
                }

                else -> {
                    ComponentButton(
                        text = stringResource(id = R.string.next),
                        contColor = Black05,
                        txtColor = Color.White,
                        iconTint = Color.White,
                        isTrailingIconButton = true,
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(start = 32.dp, bottom = 32.dp),
                        enabled = !isError
                    ) {
                        scope.launch {
                            onEvent(AvatarEvents.OnNextClicked)
                        }
                    }
                }
            }

        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                keyboard?.hide()
                onEvent(AvatarEvents.OnSnackBarShown)
            }
        }

    ) { paddingValues ->

        AvatarContent(
            modifier = Modifier.padding(paddingValues),
            bio = textFieldState.bio,
            avatar = uiState.avatar,
            isBioError = uiState.isBioError,
            onBioValueChanged = { onEvent(AvatarEvents.OnBioValueChanged(it)) }
        ) {
            onEvent(AvatarEvents.OnImageSelected(it))
        }

    }
}

@Composable
fun AvatarContent(
    modifier: Modifier = Modifier,
    bio: String,
    onBioValueChanged: (String) -> Unit,
    avatar: Uri?,
    isBioError: Boolean,
    onImageSelected: (Uri?) -> Unit

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> onImageSelected(uri) }
        )

        Box(modifier = Modifier, contentAlignment = Alignment.BottomEnd) {
            if (avatar == null) {
                Image(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(
                            1.dp, Black70,
                            CircleShape
                        )
                        .padding(24.dp),
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = "",
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(
                            1.dp, Black70,
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                    model = avatar,
                    contentDescription = ""
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Black05)
                        .clip(CircleShape)
                        .border(
                            1.dp, Color.White,
                            CircleShape
                        )
                        .padding(8.dp),
                    imageVector = Icons.Default.Edit,
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = "",
                )
            }

        }

        ComponentButton(
            text = "Add a photo",
            contColor = Black05,
            txtColor = Color.White,
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxWidth(1f)
        ) {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 64.dp), thickness = 1.dp, color = Black70
        )

        ComponentTextField(
            label = "Bio",
            modifier = Modifier.padding(top = 16.dp),
            value = bio,
            onValueChange = { onBioValueChanged(it) },
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
//                onKeyboardNextClick()
            }),
            showError = isBioError,
            errorMessage = stringResource(R.string.validate_bio_error),
            maxLines = 5,
            singleLine = false
        )
    }

}
