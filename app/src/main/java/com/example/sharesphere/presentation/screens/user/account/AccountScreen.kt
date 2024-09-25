package com.example.sharesphere.presentation.screens.user.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sharesphere.presentation.screens.chat.chat.ChatScreen
import com.example.sharesphere.presentation.screens.chat.components.ChatMessageItem
import com.example.sharesphere.presentation.screens.chat.components.ChatMessages
import com.example.sharesphere.presentation.screens.chat.components.ChatTopBar
import com.example.sharesphere.presentation.screens.chat.message.ChatMessageScreen

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    Column {
        ChatTopBar()
        Spacer(modifier=Modifier.height(32.dp))

        ChatMessages()
    }
}