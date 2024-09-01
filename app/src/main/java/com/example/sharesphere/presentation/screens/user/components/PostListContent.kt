package com.example.sharesphere.presentation.screens.user.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.sharesphere.data.commonDto.user.home.post.Post

@Composable
fun PostListContent(items: LazyPagingItems<Post>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(count = items.itemCount, key = { index -> items[index]?._id ?: 1 }
        ) { index ->
            val item = items[index]
            item?.let { PostItem(post = item) }
        }

    }
}