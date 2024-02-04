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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg


//@Preview(showSystemUi = true, showBackground = true)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Preview() {
    UsernameScreen()
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun UsernameScreen(viewModel: UsernameViewModel = hiltViewModel()) {
    val state=viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg)
            .verticalScroll(rememberScrollState()), contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back_button))
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
                value = viewModel.username,
                onValueChange = {viewModel.onEvent(UsernameEvents.onValueChange(it))},
                leadingIconImageVector = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onGo = {}),
                showError = state.value.isError,
                errorMessage = state.value.error?.asString() ?:"",
            )
            if(state.value.available){
                ComponentButton(
                    text = stringResource(R.string.next),
                    contColor = orange,
                    txtColor = Color.White,
                    modifier = Modifier.padding(top = 40.dp)
                ){

                }
            }
        }
    }
}
