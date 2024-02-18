package com.example.sharesphere.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.blackbgbtn
import com.example.sharesphere.presentation.ui.theme.blacktxt
import com.example.sharesphere.presentation.ui.theme.greydividerback
import com.example.sharesphere.presentation.ui.theme.greytxt

@Composable
fun AuthTopLayout(
    modifier: Modifier,
    onBackClick: () -> Unit,
    mainTxt: String,
    supportingTxt: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(blackbgbtn)
            .padding(32.dp), contentAlignment = Alignment.BottomStart
    ) {
        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier
                .border(
                    0.5.dp, blacktxt,
                    RoundedCornerShape(2.dp)
                )
                .size(32.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = greydividerback
            )
        }

        Column() {
            Text(
                text = supportingTxt,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                style = MaterialTheme.typography.labelSmall,
                color = greytxt
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mainTxt,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                letterSpacing = 1.sp,
                style = MaterialTheme.typography.titleLarge
            )

        }
    }
    HorizontalDivider(modifier = Modifier.height(4.dp), color = greydividerback)
}