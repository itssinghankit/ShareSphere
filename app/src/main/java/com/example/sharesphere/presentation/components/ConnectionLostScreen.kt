package com.example.sharesphere.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharesphere.presentation.ui.theme.transparentblack

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ConnectionLostScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(false, null, null){}
        .background(transparentblack)
        , contentAlignment = Alignment.BottomCenter){
        Snackbar(
            modifier = Modifier.height(24.dp)
        ) {
            Text(
                modifier=Modifier.fillMaxWidth(),
                text = "No Internet Connection",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}