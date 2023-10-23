package com.example.sharesphere.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesphere.R
import com.example.sharesphere.ui.theme.blacktxt
import com.example.sharesphere.ui.theme.darkorange
import com.example.sharesphere.ui.theme.linecolor
import com.example.sharesphere.ui.theme.orange
import com.example.sharesphere.ui.theme.orangebg

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg),
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
                fontSize = 32.sp, fontFamily = FontFamily(Font(R.font.lato_black))
            )
            Text(
                text = "Please sign in to continue",
                color = blacktxt,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.lato_regular))
            )

            TextFields("Email", Modifier.padding(top = 64.dp))
            TextFields("Password", Modifier.padding(top = 16.dp))
            Text(
                text = "Forgot Password ?", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), textAlign = TextAlign.End, fontSize = 18.sp,
                fontFamily = FontFamily(
                    Font(R.font.lato_bold)
                ),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(72.dp))
            SimpleButton("Sign in")
//            Spacer(
//                modifier = Modifier
//                    .height(40.dp)
//            )
            Divider(
                thickness = 1.dp,
                color = linecolor,
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 40.dp)
            )
            IconButton(text = "Continue with Gooogle", icon = R.drawable.google_logo)

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
                    fontSize = 18.sp
                )
            }
        }
    }

}

@Composable
fun TextFields(labeltext: String, modifier: Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    TextField(
        value = email,
        onValueChange = { email = it },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = orange,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        label = { Text(text = labeltext, color = blacktxt) },

        )
}


@Composable
fun SimpleButton(text: String) {
    val interactionSource = remember { MutableInteractionSource() }
    TextButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .indication(interactionSource = interactionSource,
                indication = rememberRipple(
                    color = darkorange
                )),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = orange)

        ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(12.dp),
            fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.lato_bold))
        )
    }
}

@Composable
fun IconButton(text: String, icon: Int) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .indication(interactionSource = interactionSource,
                indication = rememberRipple(
                    color = orange
                )),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(32.dp)

            )
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.lato_bold))
            )
        }

    }
}





