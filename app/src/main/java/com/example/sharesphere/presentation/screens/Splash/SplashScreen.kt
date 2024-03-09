package com.example.sharesphere.presentation.screens.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.R
import com.example.sharesphere.presentation.navigation.NavigationActions
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navigator: Navigator) {
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        navigator.onAction(NavigationActions.NavigateToAuthScreens)
    }
    SplashContent()
}

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg)
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column() {
            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.displaySmall,
                color = orange,
                fontFamily = FontFamily(
                    Font(
                        R.font.lato_regular
                    )
                )
            )
            Text(
                text = "ShareSphere",
                style = MaterialTheme.typography.displayMedium,
                color = orange,
                fontFamily = FontFamily(
                    Font(
                        R.font.lato_black
                    )
                )
            )
        }

    }
}
