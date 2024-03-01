package com.example.sharesphere.presentation.screens.authentication.username

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.sharesphere.presentation.ui.theme.Black70
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun UsernameScreen(
    modifier: Modifier = Modifier,
    viewModel: UsernameViewModel,
    onEvent: (UsernameEvents) -> Unit,
    onBackClick: () -> Unit,
    navigateToRegisterScreen: (username: String) -> Unit
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
    val textFieldState by viewModel.textFieldState

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
                mainTxt = stringResource(R.string.create_an_username),
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
                    enabled = uiState.isAvailable
                ) {
                    onEvent(UsernameEvents.OnNextClick)
                    navigateToRegisterScreen(textFieldState.username)
                }
            }

        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                keyboard?.hide()
                onEvent(UsernameEvents.SnackBarShown)
            }
        }

    ) { paddingValues ->


        UsernameContent(
            modifier = Modifier.padding(paddingValues),
            isUsernameError = uiState.isUsernameError,
            onNextClick = {
                scope.launch {
                    if (uiState.isAvailable) {

                        //to save username using datastore and then navigate to next screen
                        onEvent(UsernameEvents.OnNextClick)
                        navigateToRegisterScreen(textFieldState.username)

                    }
                }
            },
            onUsernameValueChange = { onEvent(UsernameEvents.OnUsernameValueChange(it)) },
            username = textFieldState.username,
            errorMessage = uiState.textFieldErrorMessage?.asString() ?: ""
        )

    }
}

@Composable
fun UsernameContent(
    modifier: Modifier,
    onNextClick: () -> Unit,
    onUsernameValueChange: (String) -> Unit,
    username: String,
    isUsernameError: Boolean,
    errorMessage: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {

        ComponentTextField(
            label = stringResource(R.string.username),
            modifier = Modifier,
            value = username,
            onValueChange = { onUsernameValueChange(it) },
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                onNextClick()
            }),
            showError = isUsernameError,
            errorMessage = errorMessage,
        )
        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = R.string.validateUsernameError),
            color = Black70,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )

    }

}



