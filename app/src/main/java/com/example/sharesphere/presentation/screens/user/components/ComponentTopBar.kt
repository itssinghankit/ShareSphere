package com.example.sharesphere.presentation.screens.user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R

@Composable
fun ComponentTopBar(
    modifier: Modifier = Modifier,
    text: String,
    showStartIcon:Boolean=true,
    startIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onStartIconClicked:()->Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            if(showStartIcon){
                IconButton(onClick = {onStartIconClicked()}) {
                    Icon(
                        imageVector = startIcon,
                        contentDescription = "saved",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                modifier = Modifier.padding(start = if(showStartIcon)2.dp else 16.dp, top = 2.dp),
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = FontFamily(Font(R.font.cirka_bold))
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}