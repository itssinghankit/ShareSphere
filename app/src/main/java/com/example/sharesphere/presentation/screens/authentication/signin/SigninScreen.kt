package com.example.sharesphere.presentation.screens.authentication.signin

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.sharesphere.presentation.ui.theme.Black13
import com.example.sharesphere.presentation.ui.theme.Violet20
import com.example.sharesphere.presentation.ui.theme.Violet40
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch


//@Composable
//fun SigninScreen(navController: NavController,navigator: Navigator) {
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    var isEmailValid by rememberSaveable { mutableStateOf(true) }
//    var isPasswordValid by rememberSaveable { mutableStateOf(true) }
//    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
//
//    val signinViewModel: SigninViewModel = hiltViewModel()
//    val signinResponse = signinViewModel.signinResponse.collectAsState()
//
//    val focusManager = LocalFocusManager.current
////    var scrollState = rememberScrollStatec
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(orangebg)
//            .verticalScroll(rememberScrollState()),
//        contentAlignment = Alignment.BottomStart
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth()
//        ) {
//            Text(
//                text = "Sign in",
//                color = orange,
//                fontSize = 32.sp,
//                fontFamily = FontFamily(Font(R.font.lato_black))
//            )
//            Text(
//                text = "Please sign in to continue",
//                color = Black13,
//                modifier = Modifier.padding(top = 8.dp),
//                fontSize = 20.sp,
//                fontFamily = FontFamily(Font(R.font.lato_regular))
//            )
//            ComponentTextField(
//                label = "Email",
//                modifier = Modifier.padding(top = 64.dp),
//                value = email,
//                onValueChange = { email = it },
//                leadingIconImageVector = Icons.Default.Email,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Email,
//                    imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//                showError = !isEmailValid,
//                errorMessage = stringResource(id = R.string.validateEmailError)
//            )
//            ComponentTextField(
//                label = "Password",
//                modifier = Modifier.padding(top = 16.dp),
//                value = password,
//                onValueChange = { password = it },
//                leadingIconImageVector = Icons.Default.Password,
//                isPasswordVisible = isPasswordVisible,
//                isPasswordField = true,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
//                showError = !isPasswordValid,
//                errorMessage = stringResource(id = R.string.validatePasswordError),
//                onVisibilityChange = { isPasswordVisible = it }
//
//            )
//            Text(
//                text = "Forgot Password ?",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp)
//                    .clickable { },
//                textAlign = TextAlign.End,
//                fontSize = 18.sp,
//                fontFamily = FontFamily(Font(R.font.lato_bold)),
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(72.dp))
//            when (signinResponse.value) {
//
//                is ApiResponse.Loading -> {
//                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth(1f)) {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .width(40.dp),
//                            color = orangebg,
//                            trackColor = orange,
//
//                            )
//                    }
//                }
//
//                is ApiResponse.Error -> {
//                    ComponentButton(
//                        text = "Sign in",
//                        contColor = orange,
//                        txtColor = Color.White,
//                        onclick = {
//                            isEmailValid = TextFieldValidation.isEmailValid(email)
//                            isPasswordValid = TextFieldValidation.isPasswordValid(password)
//                            if (isEmailValid && isPasswordValid) {
//                                signinViewModel.signin(email, password)
//                            }
//                        }
//                    )
//                }
//
//                is ApiResponse.Success -> {
//                    navController.navigate(ScreenSealedClass.AuthScreens.SigninScreen.route)
//                }
//            }
//
//            Divider(
//                thickness = 1.dp,
//                color = linecolor,
//                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
//            )
//            ComponentButton(
//                text = "Continue with Google",
//                contColor = Color.Black,
//                txtColor = Color.White,
//                isLeadingIconButton = true,
//                icon = R.drawable.google_logo,
//                onclick = {
//
//                }
//            )
//
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 64.dp)
//
//            ) {
//                Text(
//                    text = "Don't have an account ? ", color = Color.Black,
//                    fontFamily = FontFamily(Font(R.font.lato_regular)),
//                    fontSize = 18.sp
//                )
//                Text(
//                    text = stringResource(R.string.create_one),
//                    color = orange,
//                    fontFamily = FontFamily(Font(R.font.lato_bold)),
//                    modifier = Modifier.clickable { navigator.onAction(NavigationActions.NavigateToAuthScreens.NavigateToUsername) },
//                    fontSize = 18.sp
//                )
//            }
//        }
//    }
//
//}
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SigninViewModel,
    onEvent: (SignInEvents) -> Unit,
    onBackClick: () -> Unit,
    navigateToForgetPasswordScreen: () -> Unit,
    navigateToUsernameScreen: () -> Unit,
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
    val textFieldState by viewModel.textFieldStates

    val isError by derivedStateOf {
        uiState.isPasswordError || uiState.isEmailError || textFieldState.email.isEmpty() || textFieldState.password.isEmpty()
    }

    //for navigation on Home Screen
    DisposableEffect(uiState.navigateToHomeScreen) {
        if (uiState.navigateToHomeScreen) {
            navigateToHomeScreen()
        }
        onDispose {
            onEvent(SignInEvents.OnNavigationDone)
        }
    }

    //for navigation on ForgetPassword Screen
    DisposableEffect(uiState.navigateToForgetPasswordScreen) {
        if (uiState.navigateToForgetPasswordScreen) {
            navigateToForgetPasswordScreen()
        }
        onDispose {
            onEvent(SignInEvents.OnNavigationDone)
        }
    }


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
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                mainTxt = "signin to continue",
                supportingTxt = stringResource(id = R.string.sharesphere_application),
                showBackButton = false
            )
        },
        bottomBar = {
            BottomBar(
                isNetworkAvailable = networkState.isAvailable(),
                onNetworkNotAvailable = {
                    //to remove keyboard from screen and loose focus
                    focusManager.clearFocus(true)
                    keyboard?.hide()
                },
                isLoading = uiState.isLoading,
                isError = isError,
                onSignInClick = { onEvent(SignInEvents.OnNextClick) },
                onCreateOneClick = { navigateToUsernameScreen() }
            )
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                keyboard?.hide()
                onEvent(SignInEvents.OnSnackBarShown)
            }
        }

    ) { paddingValues ->

        SigninContent(
            modifier = Modifier.padding(paddingValues),
            isEmailError = uiState.isEmailError,
            isPasswordError = uiState.isPasswordError,

            onNextClick = {
                scope.launch {
                    if (!isError) {
                        //to save username using datastore and then navigate to next screen
                        onEvent(SignInEvents.OnNextClick)
                    }
                }
            },
            onEmailValueChange = { onEvent(SignInEvents.OnEmailValueChange(it)) },
            onPasswordValueChange = { onEvent(SignInEvents.OnPasswordValueChange(it)) },
            email = textFieldState.email,
            password = textFieldState.password,
            moveFocusDown = { focusManager.moveFocus(FocusDirection.Down) },
            isError = isError,
            onForgetPasswordClicked = {
                onEvent(SignInEvents.OnForgetPasswordClicked)
            }
        )

    }
}

@Composable
private fun BottomBar(
    isNetworkAvailable: Boolean,
    onNetworkNotAvailable: () -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    onCreateOneClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    if (!isNetworkAvailable) {
        onNetworkNotAvailable()
        ConnectionLostScreen()

    } else {
        Column {
            if (isLoading) {
                Loading(color = Black05)
            } else {
                ComponentButton(
                    text = stringResource(R.string.sign_in),
                    contColor = Black05,
                    txtColor = Color.White,
                    iconTint = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    enabled = !isError
                ) {
                    onSignInClick()
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 40.dp, bottom = 32.dp)

            ) {
                Text(
                    text = "Don't have an account ? ",
                    color = Black13,
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = stringResource(R.string.create_one),
                    color = Violet40,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onCreateOneClick() }

                )
            }

        }


    }
}


@Composable
fun SigninContent(
    modifier: Modifier,
    onNextClick: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    email: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    onPasswordValueChange: (String) -> Unit,
    password: String,
    moveFocusDown: () -> Boolean,
    isError: Boolean,
    onForgetPasswordClicked: () -> Unit
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
                if (!isError) {
                    onNextClick()
                }
            }),
            isPasswordField = true,
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = it },
            showError = isPasswordError,
            errorMessage = stringResource(id = R.string.validatePasswordError),
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 72.dp)
                .clickable {
                    onForgetPasswordClicked()
                },
            text = "Forgot Password ?",
            color = Black13,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.End
        )

    }

}









