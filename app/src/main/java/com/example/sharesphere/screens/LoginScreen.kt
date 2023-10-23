package com.example.sharesphere.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.toColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesphere.R

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()) {
            Text(
                text = "Sign in",
                color = colorResource(id = R.color.orange),
                fontSize = 32.sp, fontFamily = FontFamily(Font(R.font.lato_black))
            )
            Text(
                text = "Please sign in to continue",
                color = colorResource(id = R.color.blacktxt),
                modifier = Modifier.padding(0.dp, 8.dp),
                fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.lato_regular))
            )

            TextFields()
        }
    }

}

@Composable
fun TextFields() {
    val email = rememberSaveable { mutableStateOf("") }
    TextField(
        value = email.value,
        onValueChange = { email.value = it },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(containerColor= Color.Black),
        label = { Text(text = "email", color = colorResource(id = R.color.blacktxt))}
    )
}





