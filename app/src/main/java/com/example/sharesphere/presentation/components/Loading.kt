package com.example.sharesphere.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//@Composable
//fun LoadingContent(
//    loading: Boolean,
//    empty: Boolean,
//    emptyContent: @Composable () -> Unit,
//    onRefresh: () -> Unit,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    if (empty) {
//        emptyContent()
//    } else {
//        Swipe
//        SwipeRefresh(
//            state = rememberSwipeRefreshState(loading),
//            onRefresh = onRefresh,
//            modifier = modifier,
//            content = content,
//        )
//    }
//}


@Composable
fun Loading(modifier:Modifier=Modifier,color: Color = MaterialTheme.colorScheme.primary) {
    Box(modifier = modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp), color = color, strokeWidth = 3.dp)
    }
}