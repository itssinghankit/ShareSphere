package com.example.sharesphere.presentation.screens.user.viewProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.domain.model.user.profile.MyPostModel
import com.example.sharesphere.domain.model.user.profile.SavedPostModel
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel
import com.example.sharesphere.domain.use_case.user.common.comments.accountDetails.ViewAccountUseCase
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

data class ViewProfileStates(
    val errorMessage: UiText? = null,
    val isLoading: Boolean = false,
    val accountDetails: ViewAccountModel? = null,
    val userId: String? = null,
    val myPostsData: List<MyPostModel> = emptyList(),
    val savedPostsData: List<SavedPostModel> = emptyList(),
    val showDialog: Boolean = false,
    val dialogData: Post? = null
)

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val viewAccountUseCase: ViewAccountUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ViewProfileStates())
    val uiState: StateFlow<ViewProfileStates> = _uiState.asStateFlow()

    val networkState = networkMonitor.networkState

    init {
       val userId = savedStateHandle.get<String>(ViewProfileArguments.USER_ID.name)
        Timber.d("user id $userId")
    }

    fun onEvents(event: ViewProfileEvents) {
        when (event) {
            ViewProfileEvents.ResetErrorMessage -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }
        }
    }

    private fun viewAccount(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            viewAccountUseCase(userId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
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
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    accountDetails = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }

}