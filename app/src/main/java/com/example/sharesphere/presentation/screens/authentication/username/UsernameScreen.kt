package com.example.sharesphere.presentation.screens.authentication.username

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.navigation.NavigationActions
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg
import timber.log.Timber

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel,
    onEvent: (UsernameEvents) -> Unit,
    navigator: Navigator
) {

    //we can also use vieModel.onEvent but it explicitly find onEvent function and it will be time
    //taking if viewModel is large and we need to call onEvent very frequently

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    UsernameContent(state, onEvent, navigator, username = viewModel.username)

}

@Composable
fun UsernameContent(
    state: State<UsernameState>,
    onEvent: (UsernameEvents) -> Unit,
    navigator: Navigator,
    username: String
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState, snackbar = {
            SnackBarLayout(
                message = it.visuals.message,
                onEvent = onEvent
            )
        })
    }) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(orangebg)
                .verticalScroll(rememberScrollState()), contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                IconButton(onClick = { navigator.onAction(NavigationActions.NavigateBack)},) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                }
                Text(
                    text = stringResource(R.string.create_an_username),
                    color = orange,
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.lato_black)),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = stringResource(id = R.string.validateUsernameError),
                    color = blacktxt,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    modifier = Modifier.padding(top = 8.dp)
                )
                ComponentTextField(
                    label = stringResource(R.string.username),
                    modifier = Modifier.padding(top = 32.dp),
                    value = username,
                    onValueChange = { onEvent(UsernameEvents.onValueChange(it)) },
                    leadingIconImageVector = Icons.Default.Person,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onGo = {}),
                    showError = state.value.isTextfieldError,
                    errorMessage = state.value.textfieldErrorMessage?.asString() ?: ""
                )
                Timber.d("hello1")
                if (state.value.isavailable) {
                    ComponentButton(
                        text = stringResource(R.string.next),
                        contColor = orange,
                        txtColor = Color.White,
                        modifier = Modifier.padding(top = 40.dp)
                    ) {
                        navigator.onAction(NavigationActions.NavigateToSignin(username))
                        Timber.d("hello2")

                    }
                }
            }
        }

        if (state.value.showSnackBar) {

            val errorMessage=state.value.errorMessage?.asString() ?: ""
            LaunchedEffect(key1 = state.value.showSnackBar) {
                snackbarHostState.showSnackbar(errorMessage)
            }
            //we can also define context using local properties ans then pass it in asString function
        }


    }

}

@Composable
fun SnackBarLayout(
    message: String,
    onEvent: (UsernameEvents) -> Unit
) {
    Snackbar(
        modifier = Modifier
            .wrapContentSize(align = Alignment.BottomCenter)
            .padding(16.dp),
        action = {
            onEvent(UsernameEvents.snackbarShown)
        },
        containerColor = orange,
        contentColor = Color.White
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
//state.value.error?.asString() ?: ""

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun h() {
//    Snackbar(
//        modifier = Modifier.wrapContentSize(align = Alignment.BottomCenter),
//        action = {
//            // Add any action you want for the Snackbar, e.g., a dismiss action
////            viewModel.snackbarShown()
//        },
//        containerColor = orange,
//        contentColor = Color.White
//    ) {
//        Text(text = "Hello", style = MaterialTheme.typography.bodyLarge)
//    }
//}