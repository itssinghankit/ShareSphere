package com.example.sharesphere.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sharesphere.presentation.screens.authentication.mobile.MobileEvents
import com.example.sharesphere.presentation.ui.theme.orange

@Composable
fun SnackBarLayout(
    modifier: Modifier = Modifier,
    message: String,
    action: () -> Unit
) {
    Snackbar(
        modifier = modifier
            .wrapContentSize(align = Alignment.BottomCenter)
            .padding(horizontal = 32.dp, vertical = 16.dp),
        action = { action() },
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun DefinedSnackBarHost(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    action: () -> Unit
) {
    SnackbarHost(hostState = hostState, snackbar = {

        Snackbar(
            modifier = modifier
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(vertical = 16.dp),
            action = { action() },
        ) {
            Text(
                text = it.visuals.message,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

    })
}
