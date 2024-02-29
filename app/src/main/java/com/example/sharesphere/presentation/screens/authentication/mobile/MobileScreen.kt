package com.example.sharesphere.presentation.screens.authentication.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopBar
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.components.SnackBarLayout
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.presentation.ui.theme.blackbgbtn
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MobileScreen(
    modifier: Modifier = Modifier,
    viewModel: MobileViewModel,
    onEvent: (MobileEvents) -> Unit,
    onBackClick: () -> Unit,
    navigateToVerifyOtpScreen: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
        viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState by viewModel.textFieldState

    //showing snackbar
    uiState.errorMessage?.let { errorMessage ->
        val snackbarText = errorMessage.asString()
        scope.launch {
            snackbarHostState.showSnackbar(snackbarText)
        }
    }

/*
* why disposabele effect?
* Only navigates once, even if uiState.navigate changes multiple times
* Allows for cleanup actions to be performed before navigation, such as resetting the uiState.navigate flag
* More flexible than LaunchedEffect
*/
    DisposableEffect(uiState.navigate ) {
        if (uiState.navigate) {

            navigateToVerifyOtpScreen()
        }
        onDispose {
            viewModel.changeNavigateToFalse()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                mainTxt = "tell us your number",
                supportingTxt = stringResource(id = R.string.sharesphere_application)
            )
        },
        bottomBar = {
            if (!networkState.isAvailable()) {
                //to remove keyboard from screen and loose focus
                focusManager.clearFocus(true)
                keyboard?.hide()
                ConnectionLostScreen()

            } else if (uiState.isLoading) {
                Loading(color = Black05)
            } else {
                ComponentButton(
                    text = "Send OTP ",
                    contColor = blackbgbtn,
                    txtColor = Color.White,
                    iconTint = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 32.dp, bottom = 32.dp),
                    enabled = !uiState.isMobileError && textFieldState.mobile.isNotEmpty()
                ) {
                    scope.launch(Dispatchers.IO) {
                        onEvent(MobileEvents.NextClicked)
                    }
                }
            }

        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, snackbar = {
                SnackBarLayout(
                    message = it.visuals.message
                ) {
                    keyboard?.hide()
                    onEvent(MobileEvents.SnackBarShown)
                }
            })
        }

    ) { paddingValues ->


        MobileContent(
            modifier = Modifier.padding(paddingValues),
            isMobileError = uiState.isMobileError,
            onNextClick = {
                scope.launch(Dispatchers.IO) {
                    if (!uiState.isMobileError && textFieldState.mobile.isNotEmpty()) {
                        keyboard?.hide()
                        onEvent(MobileEvents.NextClicked)
                    }
                }
            },
            onMobileOtpValueChange = { onEvent(MobileEvents.MobileOnValueChange(it)) },
            mobile = textFieldState.mobile
        )

    }
}

@Composable
fun MobileContent(
    modifier: Modifier,
    onNextClick: () -> Unit,
    onMobileOtpValueChange: (String) -> Unit,
    mobile: String,
    isMobileError: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {

        ComponentTextField(
            label = stringResource(R.string.mobile_number),
            modifier = Modifier,
            value = mobile,
            onValueChange = { onMobileOtpValueChange(it) },
            leadingIconImageVector = Icons.Default.MobileFriendly,
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = {
                onNextClick()
            }),
            showError = isMobileError,
            errorMessage = stringResource(id = R.string.validateMobileError),
        )

    }

}

 
