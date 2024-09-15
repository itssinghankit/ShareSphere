package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow

interface ChatRepositoryInterface {

    suspend fun getChats(): Flow<ApiResult<List<Chat>, DataError.Network>>

}