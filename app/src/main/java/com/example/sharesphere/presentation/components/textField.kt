package com.example.sharesphere.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.DefinedFonts
import com.example.sharesphere.presentation.ui.theme.Black13
import com.example.sharesphere.presentation.ui.theme.greytxtfieldlabel

@Composable
fun ComponentDayNightTextField(
    modifier: Modifier=Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = "",
    singleLine:Boolean=true,
    maxLines:Int=1
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RectangleShape,
            maxLines = maxLines,
            label = { Text(text = label) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIconImageVector,
                    contentDescription = leadingIconDescription
                )
            },
            isError = showError,
            trailingIcon = {
                if (showError) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = stringResource(R.string.show_error_icon)
                    )
                }
            },

            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        )
        //to show error messages
        if (showError) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp, start = 2.dp, end = 2.dp)
                    .fillMaxWidth(0.9f),
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily(Font(R.font.lato_regular))
            )
        }
    }
}

@Composable
fun ComponentTextField(
    label: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconImageVector: ImageVector,
    leadingIconDescription: String = "",
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = "",
    singleLine:Boolean=true,
    maxLines:Int=1
) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Black13,
            unfocusedTextColor = Black13,
            errorTextColor = Color.Red,
            cursorColor = Black13,
            errorCursorColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedIndicatorColor = Black13,
            unfocusedIndicatorColor = greytxtfieldlabel,
            errorIndicatorColor = Color.Red,
            focusedLeadingIconColor = Black13,
            unfocusedLeadingIconColor = greytxtfieldlabel,
            errorLeadingIconColor = Color.Red,
            focusedTrailingIconColor = Black13,
            unfocusedTrailingIconColor = Black13,
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = Black13,
            unfocusedLabelColor = greytxtfieldlabel,
            errorLabelColor = Color.Red,
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = leadingIconDescription
            )
        },
        isError = showError,
        trailingIcon = {
            if (showError && !isPasswordField) Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = stringResource(R.string.show_error_icon)
            )
            if (isPasswordField) {
                IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = stringResource(R.string.toggle_password_visibility)
                    )
                }
            }
        },
        visualTransformation = when {
            isPasswordField && isPasswordVisible -> VisualTransformation.None
            isPasswordField -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.lato_regular)),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    )
    //to show error messages
    if (showError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.9f),
            fontFamily = FontFamily(Font(R.font.lato_regular))
        )
    }
}

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpCode: String,
    noOfBox: Int = 6,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        modifier = modifier,
        value = otpCode,
        onValueChange = { newValue ->
            if (newValue.length <= 6) {
                onValueChange(newValue)
            }

        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(noOfBox) { index ->
                var borderColor = greytxtfieldlabel
                val number = when {
                    index >= otpCode.length -> ""
                    else -> {
                        borderColor = Black13
                        otpCode[index].toString()
                    }
                }

                Text(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, borderColor, MaterialTheme.shapes.extraSmall)
                        .padding(4.dp),
                    text = number,
                    color = Black13,
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = DefinedFonts.latoRegular.fontFamily,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}