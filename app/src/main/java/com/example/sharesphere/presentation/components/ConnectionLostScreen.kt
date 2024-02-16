package com.example.sharesphere.presentation.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharesphere.R
import com.example.sharesphere.presentation.ui.theme.transparentblack

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ConnectionLostScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(false,null,null,{})
        .background(transparentblack)
        .padding(8.dp), contentAlignment = Alignment.BottomCenter){
        SnackBarLayout(message = stringResource(R.string.no_internet_connection)) {
            
        }
    }
}