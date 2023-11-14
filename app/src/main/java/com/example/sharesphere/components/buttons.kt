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
import com.example.sharesphere.R
import com.example.sharesphere.ui.theme.darkorange
import com.example.sharesphere.ui.theme.orange

@Composable
fun ComponentButton(
    modifier:Modifier=Modifier,
    text: String,
    contColor: Color,
    txtColor: Color,
    isIconButton: Boolean = false,
    icon: Int = 0,
    onclick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    TextButton(
        onClick = { onclick() },
        modifier = modifier
            .fillMaxWidth()
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = darkorange
                )
            ),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = contColor)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isIconButton) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(32.dp)

                )
                ButtonTxt(text = text, txtColor = txtColor, modifier = Modifier)
            } else {
                ButtonTxt(text = text, txtColor = txtColor, modifier = Modifier.padding(3.dp))
            }
        }
    }
}

@Composable
private fun ButtonTxt(
    text: String,
    txtColor: Color,
    modifier: Modifier
) {
    Text(
        text = text,
        color = txtColor,
        modifier = modifier,
        fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.lato_bold))
    )
}