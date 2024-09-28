package com.example.sharesphere.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.data.mapper.toGetAllMessagesModel
import com.example.sharesphere.data.mapper.toListGetAllMsgsModel
import com.example.sharesphere.data.remote.ShareSphereChatApi
import com.example.sharesphere.data.remote.SocketHandler
import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.data.remote.dto.chat.chat.oneone.OneOneChat
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgData
import com.example.sharesphere.data.remote.dto.chat.chatMessage.sendMsg.SendMsgReqDto
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import com.example.sharesphere.domain.repository.ChatRepositoryInterface
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class ChatRepositoryImplementation @Inject constructor(
    private val shareSphereChatApi: ShareSphereChatApi,
    private val socketHandler: SocketHandler
) : ChatRepositoryInterface {

    private val _newMessage = MutableStateFlow<GetAllMessagesModel?>(null)
    override val newMessage: StateFlow<GetAllMessagesModel?> = _newMessage.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun setupSocketListeners(userId: String) {
        socketHandler.setSocket()
        socketHandler.establishConnection()
        val mSocket = socketHandler.getSocket()

        //all events here
        mSocket.on("connected") { args ->
            Timber.d("after connection " + args[0].toString())
        }

        mSocket.on("socket-error") { args ->
            Timber.d("socket error" + args[0].toString())
        }

        mSocket.on("message-received") { args ->
            val message = args[0] as JSONObject
            Gson().fromJson(message.toString(), SendMsgData::class.java).let { data ->
                _newMessage.value = data.toGetAllMessagesModel(userId)
            }
        }

    }

    override suspend fun closeSocketConnection() {
        socketHandler.closeConnection()
    }

    override suspend fun getChats(): Flow<ApiResult<List<Chat>, DataError.Network>> = flow {
        try {
            val response = shareSphereChatApi.getChats()
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

    override suspend fun createOrGetOneOneChat(receiverId: String): Flow<ApiResult<OneOneChat, DataError.Network>> = flow {
        try {
            val response = shareSphereChatApi.createOrGetOneOneChat(receiverId)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllMessages(
        chatId: String,
        myUserId: String
    ): Flow<ApiResult<List<GetAllMessagesModel>, DataError.Network>> {
        return flow {
            try {
                val response = shareSphereChatApi.getAllMsgs(chatId).toListGetAllMsgsModel(myUserId)
                emit(ApiResult.Success(response))
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMsg(
        chatId: String,
        myUserId: String,
        content: String
    ): Flow<ApiResult<GetAllMessagesModel, DataError.Network>> = flow {
        val sendMsgReqDto = SendMsgReqDto(content)
        try {
            val response =
                shareSphereChatApi.sendMessage(chatId = chatId, sendMsgReqDto = sendMsgReqDto)
                    .toGetAllMessagesModel(myUserId)
            emit(ApiResult.Success(response))
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