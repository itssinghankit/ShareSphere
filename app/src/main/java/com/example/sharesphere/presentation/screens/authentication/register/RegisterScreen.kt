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

//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Password
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.focus.FocusManager
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.example.sharesphere.R
//import com.example.sharesphere.presentation.components.ComponentButton
//import com.example.sharesphere.presentation.components.ComponentTextField
//import com.example.sharesphere.presentation.components.ConnectionLostScreen
//import com.example.sharesphere.presentation.navigation.NavigationActions
//import com.example.sharesphere.presentation.navigation.Navigator
//import com.example.sharesphere.presentation.ui.theme.Black13
//import com.example.sharesphere.presentation.ui.theme.linecolor
//import com.example.sharesphere.presentation.ui.theme.orange
//import com.example.sharesphere.presentation.ui.theme.orangebg
//import com.example.sharesphere.util.NetworkMonitor
//
//@Composable
//fun RegisterScreen(
//    viewmodel: RegisterViewModel, onEvents: (RegisterEvents) -> Unit, navigator: Navigator
//) {
//
//    val state = viewmodel.uiState.collectAsStateWithLifecycle().value
//    val textFieldState = viewmodel.textFieldStates.value
//    val networkState =
//        viewmodel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
//    val keyboard = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current
//
//    RegisterContent(state, onEvents, navigator, textFieldState, focusManager)
//
//    if (!networkState.value.isAvailable()) {
//        //to remove keyboard from screen and loose focus
//        focusManager.clearFocus(true)
//        keyboard?.hide()
//        ConnectionLostScreen()
//    }
//}
//
//@Composable
//fun RegisterContent(
//    state: RegisterStates,
//    onEvents: (RegisterEvents) -> Unit,
//    navigator: Navigator,
//    textFieldStates: RegisterTextFieldStates,
//    focusManager: FocusManager
//) {
//
//
//    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
//    var isCPasswordVisible by rememberSaveable { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(orangebg)
//            .verticalScroll(rememberScrollState()),
//        contentAlignment = Alignment.BottomStart,
//
//        ) {
//        Column(
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth()
//        ) {
//            Text(
//                text = stringResource(R.string.sign_up),
//                color = orange,
//                fontSize = 32.sp,
//                fontFamily = FontFamily(Font(R.font.lato_black))
//            )
//            Text(
//                text = stringResource(R.string.register_yourself_to_continue),
//                color = Black13,
//                modifier = Modifier.padding(top = 8.dp),
//                fontSize = 20.sp,
//                fontFamily = FontFamily(Font(R.font.lato_regular))
//            )
//            ComponentTextField(
//                label = stringResource(R.string.email),
//                modifier = Modifier.padding(top = 64.dp),
//                value = textFieldStates.email,
//                onValueChange = { onEvents(RegisterEvents.EmailOnValueChange(it)) },
//                leadingIconImageVector = Icons.Default.Email,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//                showError = state.isEmailError,
//                errorMessage = stringResource(id = R.string.validateEmailError)
//            )
//            ComponentTextField(label = stringResource(R.string.password),
//                modifier = Modifier.padding(top = 16.dp),
//                value = textFieldStates.password,
//                onValueChange = { onEvents(RegisterEvents.PasswordOnValueChange(it)) },
//                leadingIconImageVector = Icons.Default.Password,
//                isPasswordVisible = isPasswordVisible,
//                isPasswordField = true,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//                showError = state.isPasswordError,
//                errorMessage = stringResource(id = R.string.validatePasswordError),
//                onVisibilityChange = { isPasswordVisible = it }
//
//            )
//            ComponentTextField(label = stringResource(R.string.confirm_password),
//                modifier = Modifier.padding(top = 16.dp),
//                value = textFieldStates.cPassword,
//                onValueChange = { onEvents(RegisterEvents.CPasswordOnValueChange(it)) },
//                leadingIconImageVector = Icons.Default.Password,
//                isPasswordVisible = isCPasswordVisible,
//                isPasswordField = true,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onDone = {
//                    if (
//                        textFieldStates.email.isNotEmpty() &&
//                        textFieldStates.password.isNotEmpty() &&
//                        textFieldStates.cPassword.isNotEmpty() &&
//                        !state.isEmailError &&
//                        !state.isPasswordError &&
//                        !state.isCPasswordError
//                    ) {
//                        onEvents(RegisterEvents.onNextClick)
//                        navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToMobile)
//                    }
//                }),
//                showError = state.isCPasswordError,
//                errorMessage = stringResource(id = R.string.validateCPasswordError),
//                onVisibilityChange = { isCPasswordVisible = it }
//
//            )
//            Spacer(modifier = Modifier.height(72.dp))
//
//            if (
//                textFieldStates.email.isNotEmpty() &&
//                textFieldStates.password.isNotEmpty() &&
//                textFieldStates.cPassword.isNotEmpty() &&
//                !state.isEmailError &&
//                !state.isPasswordError &&
//                !state.isCPasswordError
//            ) {
//                ComponentButton(
//                    text = stringResource(id = R.string.next),
//                    contColor = orange,
//                    txtColor = Color.White,
//                    onclick = {
//                        onEvents(RegisterEvents.onNextClick)
//                        navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToMobile)
//                    }
//                )
//            }
//            HorizontalDivider(
//                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp),
//                thickness = 1.dp,
//                color = linecolor
//            )
//            ComponentButton(
//                text = stringResource(R.string.continue_with_google),
//                contColor = Color.Black,
//                txtColor = Color.White,
//                isLeadingIconButton = true,
//                icon = R.drawable.google_logo,
//                onclick = {
//
//                }
//            )
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 64.dp)
//
//            ) {
//                Text(
//                    text = stringResource(R.string.already_have_an_account),
//                    color = Color.Black,
//                    fontFamily = FontFamily(Font(R.font.lato_regular)),
//                    fontSize = 18.sp
//                )
//                Text(text = stringResource(R.string.sign_in),
//                    color = orange,
//                    fontFamily = FontFamily(Font(R.font.lato_bold)),
//                    fontSize = 18.sp,
//                    modifier = Modifier.clickable { navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToMobile) })
//            }
//        }
//    }
//}
//
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
                    onEvent(RegisterEvents.onNextClick)
                    navigateToMobileScreen()
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
                        onEvent(RegisterEvents.onNextClick)
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

        var isPaswrodVisible by rememberSaveable {
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
            isPasswordVisible = isPaswrodVisible,
            onVisibilityChange = { isPaswrodVisible = it },
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
//ComponentTextField(label = stringResource(R.string.password),
//                modifier = Modifier.padding(top = 16.dp),
//                value = textFieldStates.password,
//                onValueChange = { onEvents(RegisterEvents.PasswordOnValueChange(it)) },
//                leadingIconImageVector = Icons.Default.Password,
//                isPasswordVisible = isPasswordVisible,
//                isPasswordField = true,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//                showError = state.isPasswordError,
//                errorMessage = stringResource(id = R.string.validatePasswordError),
//                onVisibilityChange = { isPasswordVisible = it }
//
//            )