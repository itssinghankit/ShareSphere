package com.example.sharesphere.presentation.screens.chat.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.Black05

@Composable
fun ChatMessageItem(
    modifier: Modifier = Modifier,
    isOwner: Boolean,
    name: String,
    avatar: String,
    message: String,
    time: String,
    received: Boolean? = false,
    isSameUserMsg: Boolean
) {
    val isSystemDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (isOwner) 80.dp else {
                    if (isSameUserMsg) 40.dp else 16.dp
                }, end = if (isOwner) 16.dp else 80.dp, top = if (!isSameUserMsg) 16.dp else 0.dp
            ),
        horizontalArrangement = if (isOwner) Arrangement.End else Arrangement.Start
    ) {
        if (!isOwner && !isSameUserMsg) {
            AsyncImage(
                model = avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) }
            )
        }

        Column(horizontalAlignment = if (isOwner) Alignment.End else Alignment.Start) {
            if (!isSameUserMsg) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                    Text(
                        text = if (isOwner) "You" else name,
                        style = MaterialTheme.typography.titleSmall,
                        fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = time,
                        style = MaterialTheme.typography.titleSmall,
                        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    //check status of message
                    if (isOwner) {
                        when (received) {
                            true -> {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(16.dp)
                                        .offset(y = (-2).dp),
                                    painter = painterResource(R.drawable.icon_double_tick),
                                    contentDescription = "message received",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }

                            false -> {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(16.dp)
                                        .offset(y = (-2).dp),
                                    painter = painterResource(R.drawable.icon_tick),
                                    contentDescription = "message sent",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }

                            else -> {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(14.dp)
                                        .offset(y = (-1).dp),
                                    painter = painterResource(R.drawable.icon_sending),
                                    contentDescription = "message sending",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }

                        }
                    }
                }
            }

            //actual message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isOwner) {
                    if (isSystemDarkMode) Black05 else Color.White
                } else MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                modifier = modifier
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = if (isOwner) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
                    )
                    .background(
                        color = if (isOwner) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(12.dp)
//                    .combinedClickable(onClick = {}, onLongClick = {
//                        Toast
//                            .makeText(
//                                context,
//                                "clicked",
//                                Toast.LENGTH_SHORT
//                            )
//                            .show()
//                    })
            )


        }
    }

}
