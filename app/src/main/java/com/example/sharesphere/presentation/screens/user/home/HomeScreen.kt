package com.example.sharesphere.presentation.screens.user.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen() {
    Surface {
        Text(modifier=Modifier.fillMaxSize(),text = "Home Screen", textAlign = TextAlign.Center)
    }
}