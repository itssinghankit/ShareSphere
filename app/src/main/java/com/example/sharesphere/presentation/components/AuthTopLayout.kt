package com.example.sharesphere.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.greydividerback
import com.example.sharesphere.presentation.ui.theme.greytxt

@Composable
fun AuthTopLayout(
    modifier: Modifier,
    onBackClick: () -> Unit,
    mainTxt: String,
    supportingTxt: String
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .padding(32.dp), contentAlignment = Alignment.BottomStart
    ) {

            OutlinedIconButton(
                modifier = Modifier.size(32.dp).align(Alignment.TopStart),
                onClick = onBackClick,
                shape = RectangleShape,
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column() {
                Text(
                    text = supportingTxt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mainTxt,
                    style = MaterialTheme.typography.headlineSmall,
                    color=MaterialTheme.colorScheme.onSurface
                )

            }
    }
}
