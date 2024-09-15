package com.example.sharesphere.data.repository

import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChatRepositoryImplementation @Inject constructor(
    private val shareSphereApi: ShareSphereApi
) : ChatRepositoryInterface {

    override suspend fun getChats(): Flow<ApiResult<List<Chat>, DataError.Network>> = flow {
        try {
            val response = shareSphereApi.getChats()
            emit(ApiResult.Success(response.data))
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
            }
        } catch (e: IOException) {
            emit(ApiResult.Error(DataError.Network.UNKNOWN))
        }
    }

}