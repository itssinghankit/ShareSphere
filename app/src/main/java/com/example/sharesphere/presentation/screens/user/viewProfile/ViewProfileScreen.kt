package com.example.sharesphere.presentation.screens.user.viewProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.screens.user.components.ComponentTopBar
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun ViewProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewProfileViewModel,
    onEvent: (ViewProfileEvents) -> Unit,
    onBackPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    //Showing snackBar for Errors
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = { ComponentTopBar(text = "ankitSingh", onStartIconClicked = onBackPressed) },
        bottomBar = {
            if (!networkState.isAvailable()) ConnectionLostScreen()
        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                onEvent(ViewProfileEvents.ResetErrorMessage)
            }
        }) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Loading()
            }
        } else {


        }
    }
}