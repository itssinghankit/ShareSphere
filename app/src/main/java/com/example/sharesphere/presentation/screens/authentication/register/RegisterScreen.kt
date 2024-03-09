package com.example.sharesphere.presentation.screens.authentication.register

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopBar
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch
import timber.log.Timber

//TODO: fast typing send otp enable button

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onEvent: (RegisterEvents) -> Unit,
    onBackClick: () -> Unit,
    navigateToMobileScreen: () -> Unit
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
    val textFieldState by viewModel.textFieldStates

    val isError by derivedStateOf {
        uiState.isPasswordError || uiState.isCPasswordError || uiState.isEmailError || textFieldState.email.isEmpty() || textFieldState.password.isEmpty() || textFieldState.cPassword.isEmpty()
    }

    DisposableEffect(uiState.navigate) {
        if (uiState.navigate) {
            navigateToMobileScreen()
        }
        onDispose {
            onEvent(RegisterEvents.onNavigationDone)
        }
    }


    //showing snackBar
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                mainTxt = stringResource(R.string.register_yourself_to_continue),
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
                    text = stringResource(R.string.next),
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
                    onEvent(RegisterEvents.OnNextClick)
                }
            }

        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                keyboard?.hide()
                onEvent(RegisterEvents.onSnackBarShown)
            }
        }

    ) { paddingValues ->

        RegisterContent(
            modifier = Modifier.padding(paddingValues),
            isEmailError = uiState.isEmailError,
            isPasswordError = uiState.isPasswordError,
            isCPasswordError = uiState.isCPasswordError,

            onNextClick = {
                scope.launch {
                    if (!isError) {
                        //to save username using datastore and then navigate to next screen
                        onEvent(RegisterEvents.OnNextClick)
                    }
                }
            },
            onEmailValueChange = { onEvent(RegisterEvents.OnEmailValueChange(it)) },
            onPasswordValueChange = { onEvent(RegisterEvents.OnPasswordValueChange(it)) },
            onCPasswordValueChange = { onEvent(RegisterEvents.OnCPasswordValueChange(it)) },
            email = textFieldState.email,
            password = textFieldState.password,
            cPassword = textFieldState.cPassword,
            moveFocusDown = { focusManager.moveFocus(FocusDirection.Down) },
            isError = isError
        )

    }
}


@Composable
fun RegisterContent(
    modifier: Modifier,
    onNextClick: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    email: String,
    isEmailError: Boolean,
    isCPasswordError: Boolean,
    isPasswordError: Boolean,
    onPasswordValueChange: (String) -> Unit,
    onCPasswordValueChange: (String) -> Unit,
    password: String,
    cPassword: String,
    moveFocusDown: () -> Boolean,
    isError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {

        var isPasswordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        var isCPasswordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        ComponentTextField(
            label = stringResource(R.string.email),
            modifier = Modifier,
            value = email,
            onValueChange = { onEmailValueChange(it) },
            leadingIconImageVector = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                moveFocusDown()
            }),
            showError = isEmailError,
            errorMessage = stringResource(id = R.string.validateEmailError),
        )
        ComponentTextField(
            label = stringResource(R.string.password),
            modifier = Modifier.padding(top = 16.dp),
            value = password,
            onValueChange = { onPasswordValueChange(it) },
            leadingIconImageVector = Icons.Default.Password,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                moveFocusDown()
            }),
            isPasswordField = true,
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = it },
            showError = isPasswordError,
            errorMessage = stringResource(id = R.string.validatePasswordError),
        )
        ComponentTextField(
            label = stringResource(R.string.confirm_password),
            modifier = Modifier.padding(top = 16.dp),
            value = cPassword,
            onValueChange = { onCPasswordValueChange(it) },
            leadingIconImageVector = Icons.Default.Password,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                if (!isError) {
                    onNextClick()
                }
            }),
            isPasswordField = true,
            isPasswordVisible = isCPasswordVisible,
            onVisibilityChange = { isCPasswordVisible = it },
            showError = isCPasswordError,
            errorMessage = stringResource(id = R.string.validateCPasswordError),

            )

    }

}
