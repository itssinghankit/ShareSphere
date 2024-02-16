package com.example.sharesphere.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.greytxtfieldlabel
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg
import com.example.sharesphere.presentation.ui.theme.orangetxt

@Composable
fun ComponentTextField(
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

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            , shape = RectangleShape,
        colors = TextFieldDefaults.colors(
            focusedTextColor = blacktxt,
            unfocusedTextColor = blacktxt,
            errorTextColor = Color.Red,
            cursorColor = blacktxt,
            errorCursorColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedIndicatorColor = blacktxt,
            unfocusedIndicatorColor = greytxtfieldlabel,
            errorIndicatorColor = Color.Red,
            focusedLeadingIconColor = blacktxt,
            unfocusedLeadingIconColor = greytxtfieldlabel,
            errorLeadingIconColor = Color.Red,
            focusedTrailingIconColor = blacktxt,
            unfocusedTrailingIconColor = blacktxt,
            errorTrailingIconColor = Color.Red,
            focusedLabelColor = blacktxt,
            unfocusedLabelColor = greytxtfieldlabel,
            errorLabelColor = Color.Red,
        ),
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon ={
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
        visualTransformation = when{
            isPasswordField && isPasswordVisible ->VisualTransformation.None
            isPasswordField -> PasswordVisualTransformation()
            else-> VisualTransformation.None
        },
        keyboardOptions=keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.lato_regular)), fontSize = MaterialTheme.typography.bodyLarge.fontSize)
    )
    //to show error messages
    if(showError){
        Text(
            text = errorMessage,
            color= Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.9f),
            fontFamily = FontFamily(Font(R.font.lato_regular))
        )
    }
}