package com.example.sharesphere.presentation.screens.authentication.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharesphere.R
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.util.TextFieldValidation
import com.example.sharesphere.presentation.ui.ScreenSealedClass
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.linecolor
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg


@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isEmailValid by rememberSaveable { mutableStateOf(true) }
    var isPasswordValid by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val signinViewModel: SigninViewModel = hiltViewModel()
    val signinResponse = signinViewModel.signinResponse.collectAsState()

    val focusManager = LocalFocusManager.current
//    var scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign in",
                color = orange,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.lato_black))
            )
            Text(
                text = "Please sign in to continue",
                color = blacktxt,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular))
            )
            ComponentTextField(
                label = "Email",
                modifier = Modifier.padding(top = 64.dp),
                value = email,
                onValueChange = { email = it },
                leadingIconImageVector = Icons.Default.Email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                showError = !isEmailValid,
                errorMessage = stringResource(id = R.string.validateEmailError)
            )
            ComponentTextField(
                label = "Password",
                modifier = Modifier.padding(top = 16.dp),
                value = password,
                onValueChange = { password = it },
                leadingIconImageVector = Icons.Default.Password,
                isPasswordVisible = isPasswordVisible,
                isPasswordField = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                showError = !isPasswordValid,
                errorMessage = stringResource(id = R.string.validatePasswordError),
                onVisibilityChange = { isPasswordVisible = it }

            )
            Text(
                text = "Forgot Password ?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable { },
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.lato_bold)),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(72.dp))
            when (signinResponse.value) {
//                is ApiResponse.Loading -> {
//                    ComponentButton(text = "Sign in", contColor = orange, txtColor = Color.White) {
//                        isEmailValid = TextFieldValidation.isEmailValid(email)
//                        isPasswordValid = TextFieldValidation.isPasswordValid(password)
//                        if (isEmailValid && isPasswordValid) {
//                            signinViewModel.signin(email, password)
//                        }
//                    }
//                }

                is ApiResponse.Loading -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth(1f)) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(40.dp),
                            color = orangebg,
                            trackColor = orange,

                            )
                    }
                }

                is ApiResponse.Error -> {
                    ComponentButton(text = "Sign in", contColor = orange, txtColor = Color.White) {
                        isEmailValid = TextFieldValidation.isEmailValid(email)
                        isPasswordValid = TextFieldValidation.isPasswordValid(password)
                        if (isEmailValid && isPasswordValid) {
                            signinViewModel.signin(email, password)
                        }
                    }
                }

                is ApiResponse.Success -> {
                    navController.navigate(ScreenSealedClass.LoginScreen.route)
                }
            }

            Divider(
                thickness = 1.dp,
                color = linecolor,
                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
            )
            ComponentButton(
                text = "Continue with Google",
                contColor = Color.Black,
                txtColor = Color.White,
                isIconButton = true,
                icon = R.drawable.google_logo
            ) {

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)

            ) {
                Text(
                    text = "Don't have an account ? ", color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    fontSize = 18.sp
                )
                Text(
                    text = "Create one",
                    color = orange,
                    fontFamily = FontFamily(Font(R.font.lato_bold)),
                    modifier = Modifier.clickable { navController.navigate(("register")) },
                    fontSize = 18.sp
                )
            }
        }
    }

}










