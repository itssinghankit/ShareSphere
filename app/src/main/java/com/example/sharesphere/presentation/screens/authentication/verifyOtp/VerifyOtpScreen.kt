package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopBar
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.components.OTPTextField
import com.example.sharesphere.presentation.components.SnackBarLayout
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.presentation.ui.theme.Black13
import com.example.sharesphere.presentation.ui.theme.silvertxt
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

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

//    VerifyOtpContent(state.value, onEvent, textFieldState.value, keyboard, navigator, focusManager)

    if (!networkState.value.isAvailable()) {
        //to remove keyboard from screen and loose focus
        focusManager.clearFocus(true)
        keyboard?.hide()
        ConnectionLostScreen()
    }
}

//@Composable
//fun VerifyOtpContent(
//    state: VerifyOtpStates,
//    onEvent: (VerifyOtpEvents) -> Unit,
//    textFieldState: VerifyOtpTextFieldStates,
//    keyboard: SoftwareKeyboardController?,
//    navigator: Navigator,
//    focusManager: FocusManager
//) {
//
//    val snackBarHostState = remember {
//        SnackbarHostState()
//    }
//
//    val scope = rememberCoroutineScope()
//
//    Scaffold(modifier = Modifier
//        .fillMaxSize(), snackbarHost = {
//        SnackbarHost(hostState = snackBarHostState, snackbar = {
//            SnackBarLayout(
//                message = it.visuals.message
//            ) {
//                onEvent(VerifyOtpEvents.onSnackBarShown)
//            }
//        })
//    })
//    {
//        Column(modifier = Modifier.padding(it), verticalArrangement = Arrangement.Bottom) {
//
//            AuthTopLayout(
//                modifier = Modifier.weight(2f),
//                onBackClick = { navigator.onAction(NavigationActions.NavigateBack) },
//                mainTxt = "enter your otp's",
//                supportingTxt = stringResource(id = R.string.sharesphere_application)
//            )
//            HorizontalDivider(
//                Modifier
//                    .height(4.dp)
//                    .background(MaterialTheme.colorScheme.outlineVariant)
//            )
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(5f)
//                    .background(Color.White)
//                    .padding(32.dp), contentAlignment = Alignment.BottomStart
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.TopStart)
//                ) {
//                    Text(
//                        modifier = Modifier.padding(top = 40.dp),
//                        text = stringResource(R.string.mobile_otp),
//                        color = Black13,
//                        style = MaterialTheme.typography.labelMedium
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    OTPTextField(
//                        otpCode = textFieldState.mobileOtp,
//                        onValueChange = { onEvent(VerifyOtpEvents.onMobileOtpValueChange(it)) },
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next,
//                            keyboardType = KeyboardType.NumberPassword
//                        ),
//                        keyboardActions = KeyboardActions(onNext = {
//                            focusManager.moveFocus(
//                                FocusDirection.Down
//                            )
//                        })
//                    )
//                    Spacer(modifier = Modifier.height(32.dp))
//                    Textfields(
//                        label = stringResource(id = R.string.email_otp),
//                        text = textFieldState.emailOtp,
//                        onValueChange = { onEvent(VerifyOtpEvents.onEmailOtpValueChange(it)) },
//                        onNextClick = { VerifyOtpEvents.onNextClicked },
//                        scope,
//                        state.isInputsValidated
//                    )
//                    Spacer(modifier = Modifier.height(72.dp))
//
//                    ResendButtonWithTimer {
//                        onEvent(VerifyOtpEvents.onResendClicked)
//                    }
//
//                }
//
//                ComponentButton(
//                    text = "Continue ",
//                    contColor = blackbgbtn,
//                    txtColor = Color.White,
//                    iconTint = Color.White,
//                    isTrailingIconButton = true,
//                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//                    modifier = Modifier.fillMaxWidth(0.5f),
//                    enabled = state.isInputsValidated
//                ) {
//                    onEvent(VerifyOtpEvents.onNextClicked)
//                }
//            }
//        }
//
//        if (state.showSnackBar) {
//            val errorMessage = state.errorMessage?.asString() ?: ""
//            LaunchedEffect(key1 = state.showSnackBar) {
//                keyboard?.hide()
//                snackBarHostState.showSnackbar(errorMessage)
//            }
//        }
//
//    }
//}


//@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun ss() {
//    ShareSphereTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            val viewModel: VerifyOtpViewModel = hiltViewModel()
//            VerifyOtpScree(viewModel= viewModel, onBackClick = {}, onEvent = viewModel::onEvent)
//
//        }
//    }
//}

@Composable
fun VerifyOtpScreen(
    modifier: Modifier = Modifier,
    viewModel: VerifyOtpViewModel,
    onEvent: (VerifyOtpEvents) -> Unit,
    onBackClick: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState =
        viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState by viewModel.textFieldState

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                mainTxt = "enter your otp's",
                supportingTxt = stringResource(id = R.string.sharesphere_application)
            )
        },
        bottomBar = {
            if (!networkState.value.isAvailable()) {
                //to remove keyboard from screen and loose focus
                focusManager.clearFocus(true)
                keyboard?.hide()
                ConnectionLostScreen()

            } else if (uiState.isLoading) {
                Loading(color = Black05)
            } else {
                ComponentButton(
                    text = "Continue ",
                    contColor = Black05,
                    txtColor = Color.White,
                    iconTint = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 32.dp, bottom = 32.dp),
                    enabled = uiState.isInputsValidated
                ) {

                }
            }

        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, snackbar = {
                SnackBarLayout(
                    message = it.visuals.message
                ) {
                    onEvent(VerifyOtpEvents.onSnackBarShown)
                }
            })
        }

    ) {


        VerifyOtpContent(
            modifier = Modifier.padding(it),
            onNextClick = {
                scope.launch {
                    if (uiState.isInputsValidated) {
                        Timber.d("next Clicked")
                        onEvent(VerifyOtpEvents.onNextClicked)
                    }
                }
            },
            onEmailOtpValueChange = { onEvent(VerifyOtpEvents.onEmailOtpValueChange(it)) },
            onMobileOtpValueChange = { onEvent(VerifyOtpEvents.onMobileOtpValueChange(it)) },
            emailOtp = textFieldState.emailOtp,
            mobileOtp = textFieldState.mobileOtp,
            focusManager = focusManager,
            onResendClicked = { onEvent(VerifyOtpEvents.onResendClicked) }
        )


        // Check for user messages to display on the screen
        uiState.errorMessage?.let { errorMessage ->


            Timber.d("error found")
            val snackbarText = uiState.errorMessage?.asString() ?: ""
            LaunchedEffect(errorMessage, snackbarText) {
                Timber.d("error found2")
                snackbarHostState.showSnackbar("snackbarText")
            }
        }

//        if (state.showSnackBar) {
//            val errorMessage = state.errorMessage?.asString() ?: ""
//            LaunchedEffect(key1 = state.showSnackBar) {
//                keyboard?.hide()
//                snackBarHostState.showSnackbar(errorMessage)
//            }
//        }

    }
}

@Composable
fun VerifyOtpContent(
    modifier: Modifier,
    onNextClick: () -> Unit,
    onEmailOtpValueChange: (String) -> Unit,
    onMobileOtpValueChange: (String) -> Unit,
    onResendClicked: () -> Unit,
    emailOtp: String,
    mobileOtp: String,
    focusManager: FocusManager
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {

        TextFields(
            label = stringResource(id = R.string.mobile_otp),
            text = mobileOtp,
            onValueChange = { onMobileOtpValueChange(it) },

            ) {
            focusManager.moveFocus(FocusDirection.Down)
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextFields(
            label = stringResource(id = R.string.email_otp),
            text = emailOtp,
            onValueChange = { onEmailOtpValueChange(it) }

        ) {
            onNextClick()
        }
        Spacer(modifier = Modifier.height(72.dp))

        ResendButtonWithTimer {
            onResendClicked()
        }

    }

}

@Composable
private fun TextFields(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    onNextClick: () -> Unit
) {
    Text(
        text = label,
        color = Black13,
        style = MaterialTheme.typography.labelMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    OTPTextField(
        otpCode = text,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.NumberPassword
        ),
        keyboardActions = KeyboardActions(onNext = {
            onNextClick()
        }
        )
    )
}

@Composable
fun ResendButtonWithTimer(onResendClicked: () -> Unit) {
    var isResendEnabled by rememberSaveable { mutableStateOf(false) }
    var remainingTime by rememberSaveable { mutableIntStateOf(30) }

    Timber.d("destination 1")
    LaunchedEffect(isResendEnabled) {
        if (!isResendEnabled) {
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
                withContext(Dispatchers.Main) {
                    isResendEnabled = true
                }

            }
        }

    }
    if (isResendEnabled) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isResendEnabled = false
                    remainingTime = 30
                    onResendClicked()

                },
            text = "Resend OTP's",
            color = Black13,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.End
        )
    } else {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Resend in $remainingTime seconds...",
            color = silvertxt, // Disable button while counting down
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.End
        )
    }

}
