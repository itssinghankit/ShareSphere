package com.example.sharesphere.presentation.screens.chat.components


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.presentation.ui.theme.ShareSphereTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Pre(modifier: Modifier = Modifier) {
    ShareSphereTheme {
        Surface {
            ChatTopBar()
        }
    }
}

@Composable
fun ChatMessages(modifier: Modifier = Modifier,isOwnerMessage:Boolean=false) {
    val list = listOf(false,true, false ,true, true, true, false,true,false, false, false, true)
    val isOwnerMessageState = rememberSaveable { mutableStateOf(isOwnerMessage) }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp), reverseLayout = true) {
        items(list.size){

            ChatMessageItem(isOwner = list[it])
        }
    }
}

@Composable
fun ChatTopBar(
    modifier: Modifier = Modifier,
    text: String = "Ankit Singh",
    username: String = "@itssinghankit",
    avatar: String = "https://sm.ign.com/ign_pk/cover/a/avatar-gen/avatar-generations_rpge.jpg",
    onBackBtnClicked: () -> Unit = {},
    isOnline: Boolean = true
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

            Column(modifier = Modifier
                .padding(start = 16.dp)
                .offset(y = (-4).dp)) {
                Text(
                    text = text,
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

@Composable
fun ChatMessageItem(
    modifier: Modifier = Modifier,
    isOwner: Boolean = true,
    name: String = "Ankit Singh",
    avatar: String = "https://sm.ign.com/ign_pk/cover/a/avatar-gen/avatar-generations_rpge.jpg",
    message: String = "aur bhai kya haal hai, i hope sab kuch accha hoga mai ye soch rha tha ki khi ghumne chalte kyuki mera boht man tha tere chahne na chahtne se merakuch nhi jata so bye bye tata",
    time: String = "08:34 AM",
    received: Boolean? = null
) {
    val isSystemDarkMode = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = if (isOwner) 80.dp else 16.dp, end = if (isOwner) 16.dp else 80.dp),
        horizontalArrangement = if (isOwner) Arrangement.End else Arrangement.Start
    ) {
        if (!isOwner) {
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
                                modifier= Modifier
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
                                modifier= Modifier
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
                                modifier= Modifier
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
            )


        }
    }

}