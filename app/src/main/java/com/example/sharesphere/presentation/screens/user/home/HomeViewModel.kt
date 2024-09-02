package com.example.sharesphere.presentation.screens.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.use_case.user.home.GetAllPostUseCase
import com.example.sharesphere.domain.use_case.user.home.LikePostUseCase
import com.example.sharesphere.domain.use_case.user.home.SavePostUseCase
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val isLikeError: Boolean = false,
    val likedPostId:String?=null,
    val isSaveError:Boolean=false,
    val savedPostId:String?=null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getAllPostUseCase: GetAllPostUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val savePostUseCase: SavePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeStates())
    val uiState: StateFlow<HomeStates> = _uiState

    var posts = getAllPostUseCase()
    val networkState = networkMonitor.networkState

    fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.ResetErrorMessage -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }

            is HomeEvents.LikePost -> {
                likePost(postId = event.postId)
            }

            HomeEvents.LikeErrorUpdatedSuccessfully ->{
                _uiState.update {
                    it.copy(isLikeError = false, likedPostId = null)
                }
            }

            HomeEvents.SaveErrorUpdatedSuccessfully -> {
                _uiState.update {
                    it.copy(isSaveError = false, savedPostId = null)
                }
            }

            is HomeEvents.SavePost -> {
                savePost(postId = event.postId)
            }
        }

    }

    private fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            likePostUseCase(postId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    isLikeError = true,
                                    likedPostId = postId,
                                    isLoading = false
                                )
                            }
                        }

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound)
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest)
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
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
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun savePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            savePostUseCase(postId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    isSaveError = true,
                                    savedPostId = postId,
                                    isLoading = false
                                )
                            }
                        }

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound)
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest)
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiState.update {
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
                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }

                    }
                }
            }
        }
    }


}