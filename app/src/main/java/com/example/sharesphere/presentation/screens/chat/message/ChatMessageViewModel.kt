package com.example.sharesphere.presentation.screens.chat.message

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.chat.chatMessages.GetAllMessagesModel
import com.example.sharesphere.domain.use_case.chat.chatMessages.GetAllMessagesUseCase
import com.example.sharesphere.domain.use_case.chat.chatMessages.GetNewMsgUseCase
import com.example.sharesphere.domain.use_case.chat.chatMessages.SendMsgUseCase
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

data class ChatMessageStates(
    val isLoading: Boolean = false,
    val isMessageSending: Boolean = false,
    val errorMessage: UiText? = null,
    val myUserId: String? = null,
    val chatId: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val avatar: String? = null,
    val messages: List<GetAllMessagesModel> = emptyList()
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ChatMessageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    networkMonitor: NetworkMonitor,
    private val getUserIdDataStoreUseCase: GetUserIdDataStoreUseCase,
    private val getAllMessagesUseCase: GetAllMessagesUseCase,
    private val sendMsgUseCase: SendMsgUseCase,
    private val getNewMsgUseCase: GetNewMsgUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow(ChatMessageStates())
    val uiStates: StateFlow<ChatMessageStates> = _uiStates.asStateFlow()

    var myMessage by mutableStateOf("")
        private set

    val networkState = networkMonitor.networkState

    init {
        val chatId = savedStateHandle.get<String>(ChatMessageArguments.CHAT_ID.name)
        val fullName = savedStateHandle.get<String>(ChatMessageArguments.FULL_NAME.name)
        val username = savedStateHandle.get<String>(ChatMessageArguments.USERNAME.name)
        val avatar = Uri.decode(savedStateHandle.get<String>(ChatMessageArguments.AVATAR.name))

        viewModelScope.launch {
            _uiStates.update {
                it.copy(
                    myUserId = getUserIdDataStoreUseCase(),
                    chatId = chatId,
                    fullName = fullName,
                    username = username,
                    avatar = avatar
                )
            }
            getAllMessages()
        }

        //listening for new message
        listenForNewMessage()

    }

    private fun listenForNewMessage() {

        viewModelScope.launch {
            getNewMsgUseCase().collect { newMessage ->

                newMessage?.let { message ->
                    //check if the message is of this chat or of another chat
                    //also it is checking whether last message and new message is not same to avoid
                    // unnecessary addition of message in list though keys in lazy column can
                    // handle everything but still why take chance
                    //also have to handle empty message list
                    if (newMessage.chatId == uiStates.value.chatId && if (uiStates.value.messages.isNotEmpty()) uiStates.value.messages[0]._id != newMessage._id else false) {

                        val updatedMessages = uiStates.value.messages.toMutableList()
                        uiStates.value.myUserId?.let {
                            updatedMessages.add(
                                0, message
                            )
                            _uiStates.update {
                                it.copy(
                                    messages = updatedMessages
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    fun onEvent(event: ChatMessageEvents) {
        when (event) {
            ChatMessageEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }

            is ChatMessageEvents.OnMessageValueChanged -> {
                myMessage = event.message
            }

            ChatMessageEvents.OnMessageSendButtonClicked -> {
                val message = myMessage
                myMessage = ""
                sendMessage(message)

            }
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _uiStates.update {
                    it.copy(
                        isMessageSending = true
                    )
                }
            }
            uiStates.value.myUserId?.let { myUserId ->
                uiStates.value.chatId?.let { chatId ->
                    sendMsgUseCase(
                        myUserId = myUserId,
                        chatId = chatId,
                        content = message
                    ).collect { result ->
                        when (result) {
                            is ApiResult.Error -> {
                                when (result.error) {
                                    DataError.Network.INTERNAL_SERVER_ERROR -> {
                                        withContext(Dispatchers.Main) {
                                            _uiStates.update {
                                                it.copy(
                                                    errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                                    isMessageSending = false
                                                )
                                            }
                                        }
                                    }

                                    else -> {
                                        withContext(Dispatchers.Main) {
                                            _uiStates.update {
                                                it.copy(
                                                    errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                                    isMessageSending = false
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            is ApiResult.Success -> {
                                val updatedMessages = uiStates.value.messages.toMutableList()
                                updatedMessages.add(0, result.data)
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            messages = updatedMessages,
                                            isMessageSending = false
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _uiStates.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            uiStates.value.myUserId?.let { myUserId ->
                uiStates.value.chatId?.let { chatId ->
                    getAllMessagesUseCase(myUserId = myUserId, chatId = chatId).collect { result ->
                        when (result) {
                            is ApiResult.Error -> {

                                when (result.error) {
                                    DataError.Network.INTERNAL_SERVER_ERROR -> {
                                        withContext(Dispatchers.Main) {
                                            _uiStates.update {
                                                it.copy(
                                                    errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                                    isLoading = false
                                                )
                                            }
                                        }
                                    }

                                    else -> {
                                        withContext(Dispatchers.Main) {
                                            _uiStates.update {
                                                it.copy(
                                                    errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                                    isLoading = false
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            is ApiResult.Success -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            messages = result.data, isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}