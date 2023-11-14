package com.example.sharesphere.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sharesphere.ui.theme.blacktxt
import com.example.sharesphere.ui.theme.orange

@Composable
fun InputTextField(
    label: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    isPasswordField: Boolean=false,
    isPasswordVisible: Boolean=false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean=false,
    errorMessage: String = ""
) {

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = orange,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        label = { Text(text = label, color = blacktxt) },
        leadingIcon ={
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = leadingIconDescription,
                tint = if (showError) (MaterialTheme.colorScheme.error) else (MaterialTheme.colorScheme.onSurface)
            )
        },
        isError = showError,
        trailingIcon = {
            if (showError && !isPasswordField) Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = "show error icon"
            )
            if (isPasswordField) {
                IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription ="Toggle password visibility"
                    )
                }
            }
        },
        visualTransformation = when{
            isPasswordField && isPasswordVisible ->VisualTransformation.None
            isPasswordField -> PasswordVisualTransformation()
            else-> VisualTransformation.None
        },
        keyboardOptions=keyboardOptions,
        keyboardActions = keyboardActions,
    )
    //to show error messages
    if(showError){
        Text(
            text = errorMessage,
            color= MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier =Modifier.fillMaxWidth(0.9f)
        )
    }
}

