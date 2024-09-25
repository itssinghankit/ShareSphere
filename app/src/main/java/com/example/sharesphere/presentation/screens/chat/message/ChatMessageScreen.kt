package com.example.sharesphere.presentation.screens.chat.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChatMessageScreen() {
    ChatMessageContent()
}

@Composable
fun ChatMessageContent(modifier: Modifier = Modifier) {

    val name = "Ankit Singh"

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            //do some thing
            //spacer width
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "online",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun MessageTextField(modifier: Modifier = Modifier, text: String, onValueChange: (String) -> Unit) {

    TextField(
        value = text,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        shape = RoundedCornerShape(160.dp),
        placeholder = {
            Text(text="Type a message")
        },
        leadingIcon = {},
        trailingIcon = {}
    )

}
