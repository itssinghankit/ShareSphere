package com.example.sharesphere.presentation.screens.chat.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.data.remote.dto.chat.chat.Chat
import com.example.sharesphere.domain.model.user.common.UserItemModel
import com.example.sharesphere.domain.use_case.chat.GetChatsUseCase
import com.example.sharesphere.domain.use_case.user.common.follow.FollowUserUseCase
import com.example.sharesphere.domain.use_case.user.common.search.SearchUserUseCase
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ChatStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val searchResult: List<UserItemModel>? = null,
    val userId: String? = null,
    val chats: List<Chat> = emptyList()
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val searchUserUseCase: SearchUserUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val getUserIdDataStoreUseCase: GetUserIdDataStoreUseCase,
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow(ChatStates())
    val uiStates: StateFlow<ChatStates> = _uiStates.asStateFlow()

    val networkState = networkMonitor.networkState

    var searchQuery by mutableStateOf("")
        private set

    private var job: Job? = null

    init {
        //fetch all chats available
        getChats()

        //fetch userid for further processing
        viewModelScope.launch {
            val userId = getUserIdDataStoreUseCase()
            _uiStates.update {
                it.copy(userId = userId)
            }
        }
    }

    fun onEvent(event: ChatEvents) {
        when (event) {
            ChatEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(errorMessage = null)
                }
            }

            is ChatEvents.OnSearchQueryChanged -> {
                searchQuery = event.searchQuery
                job?.cancel()
                if (searchQuery.isNotEmpty()) {
                    job = viewModelScope.launch {
                        delay(500)
                        searchUser(searchQuery)
                    }
                } else {
                    onEvent(ChatEvents.OnSearchActiveClosed)
                    _uiStates.update {
                        it.copy(searchResult = null)
                    }
                }

            }

            is ChatEvents.OnFollowClicked -> {
                followUser(event.userId)
            }

            ChatEvents.OnSearchActiveClosed -> {
                job?.cancel()
                _uiStates.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }


        }
    }

    private fun searchUser(usernameOrName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }
            searchUserUseCase(usernameOrName).collect { result ->
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
                                    isLoading = false,
                                    searchResult = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun followUser(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            followUserUseCase(accountId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        //do nothing
                    }
                }
            }
        }
    }

    private fun getChats() {
        viewModelScope.launch(Dispatchers.IO) {

            getChatsUseCase().collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet)
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
                                    chats = result.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}