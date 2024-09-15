package com.example.sharesphere.presentation.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sharesphere.R

@Preview
@Composable
private fun previewChat() {
    ChatItem(name = "Ankit Singh",lastMessage="Bhai kha hai abhi tak nhi aaya sab thik to hai")
}

@Composable
fun ChatItem(modifier: Modifier = Modifier,name:String,lastMessage:String,time:String="12:00",messageLeft:Int=0) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/1",
                contentDescription = "avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) },
            )
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text =name ,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = lastMessage,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular))
                )
            }
        }
        Column(Modifier.height(48.dp),horizontalAlignment = Alignment.End) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = if(messageLeft>0)MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.gilroy_regular))
            )
            if(messageLeft>0){
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            CircleShape
                        )
                        .padding(4.dp),
                    text = "$messageLeft",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold))
                )
            }
        }

    }
}