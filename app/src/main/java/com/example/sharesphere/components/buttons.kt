package com.example.sharesphere.components

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharesphere.R
import com.example.sharesphere.screens.authentication.register.RegisterViewModel
import com.example.sharesphere.ui.theme.darkorange
import com.example.sharesphere.ui.theme.orange

@Composable
fun SimpleTextButton(text: String, funct:()-> Unit) {

    val signupViewmodel:RegisterViewModel= hiltViewModel()

    val interactionSource = remember { MutableInteractionSource() }
    TextButton(
        onClick = {funct()},
        modifier = Modifier
            .fillMaxWidth()
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = darkorange
                )
            ),
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
fun IconTextButton(text: String, icon: Int) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = orange
                )
            ),
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