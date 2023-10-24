package com.example.sharesphere.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sharesphere.R
import com.example.sharesphere.components.IconTextButton
import com.example.sharesphere.components.InputTextField
import com.example.sharesphere.components.SimpleTextButton
import com.example.sharesphere.ui.theme.blacktxt
import com.example.sharesphere.ui.theme.linecolor
import com.example.sharesphere.ui.theme.orange
import com.example.sharesphere.ui.theme.orangebg

@Composable
fun RegisterScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.BottomStart,

        ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Sign up",
                color = orange,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.lato_black))
            )
            Text(
                text = "Register yourself to continue",
                color = blacktxt,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular))
            )
            InputTextField(
                labeltext = "Name",
                modifier = Modifier.padding(top = 64.dp),
                visualTransformation = VisualTransformation.None
            )
            InputTextField(
                labeltext = "Email",
                modifier = Modifier.padding(top = 16.dp),
                visualTransformation = VisualTransformation.None
            )
            InputTextField(
                labeltext = "Password",
                modifier = Modifier.padding(top = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(72.dp))
            SimpleTextButton(text = "Sign up")
            Divider(
                thickness = 1.dp,
                color = linecolor,
                modifier = Modifier.padding(top = 16.dp, bottom = 40.dp)
            )
            IconTextButton(text = "Continue with Gooogle", icon = R.drawable.google_logo)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)

            ) {
                Text(
                    text = "Already have an account ? ", color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    fontSize = 18.sp
                )
                Text(
                    text = "Sign in",
                    color = orange,
                    fontFamily = FontFamily(Font(R.font.lato_bold)),
                    fontSize = 18.sp,
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }

}