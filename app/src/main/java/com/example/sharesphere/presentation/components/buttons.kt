package com.example.sharesphere.presentation.components

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.greydisbledbtn
import com.example.sharesphere.presentation.ui.theme.greytxt
import com.example.sharesphere.presentation.ui.theme.whitetxt

@Composable
fun ComponentButton(
    modifier: Modifier = Modifier,
    text: String,
    contColor: Color,
    txtColor: Color,
    isLeadingIconButton: Boolean = false,
    icon: Int = 0,
    isTrailingIconButton: Boolean = false,
    imageVector: ImageVector = Icons.Default.ArrowBack,
    iconTint: Color = whitetxt,
    rippleColor: Color = greytxt,
    shape: Shape = RectangleShape,
    disabledContainerColor: Color = greydisbledbtn,
    enabled: Boolean = true,
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
                    color = rippleColor
                )
            ),
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = contColor,
            disabledContainerColor = disabledContainerColor
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLeadingIconButton) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(32.dp)

                )
                ButtonTxt(text = text, txtColor = txtColor, modifier = Modifier)
            } else if (isTrailingIconButton) {
                ButtonTxt(text = text, txtColor = txtColor, modifier = Modifier.padding(4.dp))
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = iconTint
                )
            } else {
                ButtonTxt(text = text, txtColor = txtColor, modifier = Modifier.padding(3.dp))
            }
        }
    }
}

@Composable
fun ButtonTxt(
    text: String,
    txtColor: Color,
    modifier: Modifier
) {
    Text(
        text = text,
        color = txtColor,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = FontFamily(Font(R.font.lato_bold))
    )
}