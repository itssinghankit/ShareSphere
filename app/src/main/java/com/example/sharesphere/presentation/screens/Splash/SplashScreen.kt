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
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.orange
import com.example.sharesphere.presentation.ui.theme.orangebg
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
    navigateToHomeScreen: () -> Unit,
    navigateToSignInScreen: () -> Unit,
    navigateToMobileScreen: () -> Unit,
    navigateToDetailsScreen: () -> Unit
) {

    val state = viewModel.state

    LaunchedEffect(key1 = state.navigate) {
        delay(2000)
       if(state.navigate){
           if (state.isDetailsFilled) {
               navigateToHomeScreen()
           } else {
               if (state.isVerified) {
                   navigateToDetailsScreen()
               } else {
                   if (state.isSignedUp) {
                       navigateToMobileScreen()
                   } else {
                       navigateToSignInScreen()
                   }
               }
           }
       }
       }
    SplashContent(modifier)
}

@Composable
fun SplashContent(modifier: Modifier) {
    Box(
        modifier = modifier
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
