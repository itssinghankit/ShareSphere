package com.example.sharesphere.presentation.screens.chat.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R


@Composable
fun ChatTopBar(
    modifier: Modifier = Modifier,
    name: String,
    username: String,
    avatar: String,
    onBackBtnClicked: () -> Unit,
    isOnline: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onBackBtnClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "saved",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Box {
                AsyncImage(
                    model = avatar,
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) }
                )
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .offset(y = (-4).dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.cirka_bold))
                )
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.cirka_bold))
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
