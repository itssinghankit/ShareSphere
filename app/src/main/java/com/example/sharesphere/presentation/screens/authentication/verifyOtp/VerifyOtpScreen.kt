package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopLayout
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.OTPTextField
import com.example.sharesphere.presentation.components.SnackBarLayout
import com.example.sharesphere.presentation.navigation.NavigationActions
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.DefinedFonts
import com.example.sharesphere.presentation.ui.theme.blackbgbtn
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.silvertxt
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.coroutineContext

@Composable
fun VerifyOtpScreen(
    viewModel: VerifyOtpViewModel,
    onEvent: (VerifyOtpEvents) -> Unit,
    navigator: Navigator
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState = viewModel.textFieldState
    val networkState =
        viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    VerifyOtpContent(state.value, onEvent, textFieldState.value, keyboard, navigator, focusManager)

    if (!networkState.value.isAvailable()) {
        //to remove keyboard from screen and loose focus
        focusManager.clearFocus(true)
        keyboard?.hide()
        ConnectionLostScreen()
    }
}

@Composable
fun VerifyOtpContent(
    state: VerifyOtpStates,
    onEvent: (VerifyOtpEvents) -> Unit,
    textFieldState: VerifyOtpTextFieldStates,
    keyboard: SoftwareKeyboardController?,
    navigator: Navigator,
    focusManager: FocusManager
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier
        .fillMaxSize(), snackbarHost = {
        SnackbarHost(hostState = snackBarHostState, snackbar = {
            SnackBarLayout(
                message = it.visuals.message
            ) {
                onEvent(VerifyOtpEvents.onSnackBarShown)
            }
        })
    })
    {
        Column(modifier = Modifier.padding(it), verticalArrangement = Arrangement.Bottom) {

            AuthTopLayout(
                modifier = Modifier.weight(2f),
                onBackClick = { navigator.onAction(NavigationActions.NavigateBack) },
                mainTxt = "enter your otp's",
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
                    Text(
                        text = stringResource(R.string.mobile_otp),
                        fontFamily = DefinedFonts.latoBold.fontFamily,
                        color = blacktxt,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OTPTextField(
                        otpCode = textFieldState.mobileOtp,
                        onValueChange = { onEvent(VerifyOtpEvents.onMobileOtpValueChange(it)) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Down
                            )
                        })
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(R.string.email_otp),
                        fontFamily = DefinedFonts.latoBold.fontFamily,
                        color = blacktxt,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OTPTextField(
                        otpCode = textFieldState.emailOtp,
                        onValueChange = { onEvent(VerifyOtpEvents.onEmailOtpValueChange(it)) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            scope.launch {
                                if (state.isInputsValidated) {
                                    Timber.d("next Clicked")
                                    onEvent(VerifyOtpEvents.onNextClicked)
                                }
                            }

                        })
                    )
                    Spacer(modifier = Modifier.height(32.dp))

//                    ResendButtonWithTimer{
//                        onEvent(VerifyOtpEvents.onResendClicked)
//                    }

                }

                ComponentButton(
                    text = "Continue ",
                    contColor = blackbgbtn,
                    txtColor = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    enabled = state.isInputsValidated
                ) {
                    onEvent(VerifyOtpEvents.onNextClicked)
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

@Composable
fun ResendButtonWithCounter(
    isCountingDown: Boolean, // Track the counting down state
    timeRemaining: Int, // Store the remaining time in seconds
    onResendClicked: () -> Unit, // Callback for resend action
    startCountdown: (Int) -> Unit
) {
    if (isCountingDown) {
        Text(
            text = "Resend in $timeRemaining seconds...",
            color = silvertxt, // Disable button while counting down
            style = MaterialTheme.typography.titleSmall
        )
    } else {
        Text(
            modifier = Modifier.clickable {
                onResendClicked()
                // Start the countdown after resend
                startCountdown(30)
            },
            text = "Resend OTP's",
            fontFamily = DefinedFonts.latoBold.fontFamily,
            color = blacktxt,
            style = MaterialTheme.typography.titleSmall
        )

    }
}

@Composable
fun ResendButtonWithTimer(onResendClicked: () -> Unit) {
    var isResendEnabled by rememberSaveable { mutableStateOf(false) }
    var remainingTime by rememberSaveable { mutableIntStateOf(30) }

    Timber.d("destination 1")
    LaunchedEffect(isResendEnabled) {
        if(!isResendEnabled){
            Timber.d("destination 2")
            withContext(Dispatchers.IO) {
                var timeRemaining = remainingTime
                while (timeRemaining > 0) {
                    Timber.d("destination 3")
                    delay(1000)
                    timeRemaining--

                    withContext(Dispatchers.Main) {
                        remainingTime = timeRemaining
                    }
                }
                Timber.d("destination 4")
                withContext(Dispatchers.Main){
                    isResendEnabled=true
                }

            }
        }

    }
    if (isResendEnabled) {
        Text(
            modifier = Modifier.clickable {
                isResendEnabled=false
                remainingTime=30
                onResendClicked()

            },
            text = "Resend OTP's",
            fontFamily = DefinedFonts.latoBold.fontFamily,
            color = blacktxt,
            style = MaterialTheme.typography.titleSmall
        )
    } else {
        Text(
            text = "Resend in $remainingTime seconds...",
            color = silvertxt, // Disable button while counting down
            style = MaterialTheme.typography.titleSmall
        )
    }

}

