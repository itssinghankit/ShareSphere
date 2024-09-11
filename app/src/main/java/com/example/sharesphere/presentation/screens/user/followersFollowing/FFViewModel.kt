package com.example.sharesphere.presentation.screens.user.followersFollowing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.user.common.UserItemModel
import com.example.sharesphere.domain.use_case.user.common.follow.FollowUserUseCase
import com.example.sharesphere.domain.use_case.user.followersFollowing.GetFollowersUseCase
import com.example.sharesphere.domain.use_case.user.followersFollowing.GetFollowingUseCase
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
import javax.inject.Inject

data class FFStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val followersData: List<UserItemModel> = emptyList(),
    val followingData: List<UserItemModel> = emptyList(),
    val userId: String? = null,
    val followersTab: Boolean? = null,
    val username:String?=null
)

@HiltViewModel
class FFViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val savedStateHandle: SavedStateHandle,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getFollowingUseCase: GetFollowingUseCase,
    private val followUserUseCase: FollowUserUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow(FFStates())
    val uiStates: StateFlow<FFStates> = _uiStates.asStateFlow()

    val networkState = networkMonitor.networkState

    init {
        val userId = savedStateHandle.get<String>(FFArguments.USER_ID.name)
        val followers = savedStateHandle.get<Boolean>(FFArguments.FOLLOWERS.name)
        val username =savedStateHandle.get<String>(FFArguments.USERNAME.name)
        _uiStates.update {
            it.copy(userId = userId, followersTab = followers, username = username)
        }
        getFollowers(userId)
        getFollowing(userId)
    }

    fun onEvent(event: FFEvents) {
        when (event) {
            FFEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(errorMessage = null)
                }
            }

            is FFEvents.FollowUser -> {
                followUser(event.accountId)
            }
        }
    }

    private fun getFollowers(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }

            userId?.let {
                getFollowersUseCase(userId).collect { result ->
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
                                        followersData = result.data
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    private fun getFollowing(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }

            userId?.let {
                getFollowingUseCase(userId).collect { result ->
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
                                        followingData = result.data
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    private fun followUser(accountId:String) {
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

}