package com.example.sharesphere.presentation.screens.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.commonDto.user.home.post.PostedBy
import com.example.sharesphere.domain.model.user.profile.MyPostModel
import com.example.sharesphere.domain.model.user.profile.SavedPostModel
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel
import com.example.sharesphere.domain.use_case.user.profile.GetMyPostsUseCase
import com.example.sharesphere.domain.use_case.user.profile.GetSavedPostsUseCase
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import com.example.sharesphere.domain.use_case.user.profile.ViewAccountUseCase
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

data class ProfileStates(
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
class ProfileViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val viewAccountUseCase: ViewAccountUseCase,
    private val getUserIdDataStoreUseCase: GetUserIdDataStoreUseCase,
    private val getMyPostsUseCase: GetMyPostsUseCase,
    private val getSavedPostsUseCase: GetSavedPostsUseCase
) : ViewModel() {

    init {
        fetchUserIdDataStore()
    }

    private val _uiState = MutableStateFlow(ProfileStates())
    val uiState: StateFlow<ProfileStates> = _uiState.asStateFlow()

    val networkState = networkMonitor.networkState

    fun onEvent(event: ProfileEvents) {
        when (event) {
            ProfileEvents.ResetErrorMessage -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }

            is ProfileEvents.ShowImagesDialog -> {
                if (event.isMyPostDialog == true) {
                    uiState.value.accountDetails?.let { accountDetails ->
                        _uiState.update {
                            it.copy(
                                showDialog = true,
                                dialogData = Post(
                                    __v = 0,
                                    _id = _uiState.value.myPostsData[event.index]._id,
                                    caption = _uiState.value.myPostsData[event.index].caption,
                                    commentCount = _uiState.value.myPostsData[event.index].commentCount,
                                    likeCount = _uiState.value.myPostsData[event.index].likeCount,
                                    updatedAt = "",
                                    createdAt = "",
                                    isSaved = false,
                                    isFollowed = true,
                                    isLiked = false,
                                    postImages = _uiState.value.myPostsData[event.index].postImages,
                                    postedBy = PostedBy(
                                        _id = accountDetails._id,
                                        avatar = accountDetails.avatar,
                                        fullName = accountDetails.fullName,
                                        username = accountDetails.username
                                    )
                                )
                            )
                        }
                    }
                } else {
                    //save post dialog
                    _uiState.update {
                        it.copy(
                            showDialog = true,
                            dialogData = Post(
                                __v = 0,
                                _id = _uiState.value.savedPostsData[event.index].postDetails._id,
                                caption = _uiState.value.savedPostsData[event.index].postDetails.caption,
                                commentCount = _uiState.value.savedPostsData[event.index].postDetails.commentCount,
                                likeCount = _uiState.value.savedPostsData[event.index].postDetails.likeCount,
                                updatedAt = _uiState.value.savedPostsData[event.index].postDetails.updatedAt,
                                createdAt = _uiState.value.savedPostsData[event.index].postDetails.createdAt,
                                isSaved = false,
                                isFollowed = true,
                                isLiked = false,
                                postImages = _uiState.value.savedPostsData[event.index].postDetails.postImages,
                                postedBy = PostedBy(
                                    _id = _uiState.value.savedPostsData[event.index].postedBy._id,
                                    avatar = _uiState.value.savedPostsData[event.index].postedBy.avatar,
                                    fullName = _uiState.value.savedPostsData[event.index].postedBy.fullName,
                                    username = _uiState.value.savedPostsData[event.index].postedBy.username
                                )
                            )
                        )
                    }
                }
            }

            ProfileEvents.OnDismissDialogClicked -> {
                _uiState.update {
                    it.copy(
                        showDialog = false,
                        dialogData = null
                    )
                }
            }
        }
    }

    private fun fetchUserIdDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = getUserIdDataStoreUseCase()
            withContext(Dispatchers.Main) {
                _uiState.update {
                    it.copy(userId = userId)
                }
            }
            viewAccount(userId)
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
                        fetchMyPostsData()
                        fetchSavedPostsData()
                        withContext(Dispatchers.Main) {
                            Timber.d("ViewAccountModel: ${result.data}")
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

    private fun fetchMyPostsData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            getMyPostsUseCase().collect { result ->
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
                                    myPostsData = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun fetchSavedPostsData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            getSavedPostsUseCase().collect { result ->
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
                                    savedPostsData = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }


}