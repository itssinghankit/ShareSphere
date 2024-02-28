package com.example.sharesphere.presentation.screens.authentication.mobile

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopLayout
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.SnackBarLayout
import com.example.sharesphere.presentation.navigation.NavigationActions
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.blackbgbtn
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch


@Composable
fun MobileScreen(
    viewModel: MobileViewModel,
    onEvent: (MobileEvents) -> Unit,
    navigator: Navigator
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState = viewModel.textFieldState
    val networkState =
        viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    MobileContent(state.value, textFieldState.value, onEvent, navigator, keyboard)

    if (!networkState.value.isAvailable()) {
        //to remove keyboard from screen and loose focus
        focusManager.clearFocus(true)
        keyboard?.hide()
        ConnectionLostScreen()
    }

}


@Composable
fun MobileContent(
    state: MobileStates,
    textFieldState: MobileTextFieldStates,
    onEvent: (MobileEvents) -> Unit,
    navigator: Navigator,
    keyboard: SoftwareKeyboardController?,


    ) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState, snackbar = {
            SnackBarLayout(
                message = it.visuals.message
            ) {
                onEvent(MobileEvents.SnackBarShown)
            }
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Bottom
        ) {

            AuthTopLayout(
                modifier = Modifier.weight(2f),
                onBackClick = { navigator.onAction(NavigationActions.NavigateBack) },
                mainTxt = stringResource(id = R.string.tell_us_your_mobile_number),
                supportingTxt = stringResource(id = R.string.sharesphere_application)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
                    .background(Color.White)
                    .padding(32.dp), contentAlignment = Alignment.BottomStart
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    ComponentTextField(
                        label = stringResource(R.string.mobile_number),
                        modifier = Modifier,
                        value = textFieldState.mobile,
                        onValueChange = { onEvent(MobileEvents.MobileOnValueChange(it)) },
                        leadingIconImageVector = Icons.Default.MobileFriendly,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(onGo = {
                            scope.launch {
                                if (state.isMobileError && textFieldState.mobile.isNotEmpty()) {
                                    onEvent(MobileEvents.NextClicked)
                                }
                            }

                        }),
                        showError = state.isMobileError,
                        errorMessage = stringResource(id = R.string.validateMobileError),
                    )
                }

                ComponentButton(
                    text = "Continue ",
                    contColor = blackbgbtn,
                    txtColor = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = !state.isMobileError && textFieldState.mobile.isNotEmpty()
                ) {
                    onEvent(MobileEvents.NextClicked)
                }
            }
        }

        if (state.showSnackBar) {
            val errorMessage = state.errorMessage?.asString() ?: ""
            LaunchedEffect(key1 = state.showSnackBar) {
                keyboard?.hide()
                snackBarHostState.showSnackbar(errorMessage)
            }
        }
    }
}
 
