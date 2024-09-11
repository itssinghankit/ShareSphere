package com.example.sharesphere.presentation.screens.user.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.user.common.ShowCommentsModel
import com.example.sharesphere.domain.model.user.profile.ViewAccountModel
import com.example.sharesphere.domain.use_case.user.common.comments.accountDetails.ViewAccountUseCase
import com.example.sharesphere.domain.use_case.user.common.comments.addComment.AddCommentUseCase
import com.example.sharesphere.domain.use_case.user.common.comments.showComments.GetCommentsUseCase
import com.example.sharesphere.domain.use_case.user.common.follow.FollowUserUseCase
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val isLikeError: Boolean = false,
    val likedPostId: String? = null,
    val isSaveError: Boolean = false,
    val savedPostId: String? = null,
    val commentsData: List<ShowCommentsModel> = emptyList(),
    val isCommentsLoading: Boolean = false,
    val commentPostId: String? = null,
    val accountDetails: ViewAccountModel? = null,
    val userId: String? = null,
    val addedCommentCount:Int=0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getAllPostUseCase: GetAllPostUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val savePostUseCase: SavePostUseCase,
    private val getUserIdDataStoreUseCase: GetUserIdDataStoreUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val viewAccountUseCase: ViewAccountUseCase,
) : ViewModel() {

    private val _uiStates = MutableStateFlow(HomeStates())
    val uiStates: StateFlow<HomeStates> = _uiStates

    var posts = getAllPostUseCase()
    val networkState = networkMonitor.networkState

    var comment by mutableStateOf("")
        private set

    init {
        fetchUserIdDataStore()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(errorMessage = null)
                }
            }

            is HomeEvents.LikePost -> {
                likePost(postId = event.postId)
            }

            HomeEvents.LikeErrorUpdatedSuccessfully -> {
                _uiStates.update {
                    it.copy(isLikeError = false, likedPostId = null)
                }
            }

            HomeEvents.SaveErrorUpdatedSuccessfully -> {
                _uiStates.update {
                    it.copy(isSaveError = false, savedPostId = null)
                }
            }

            is HomeEvents.SavePost -> {
                savePost(postId = event.postId)
            }

            is HomeEvents.OnFollowClicked -> {
                followUser(event.userId)
            }

            is HomeEvents.ShowComments -> {
                _uiStates.update {
                    it.copy(
                        commentPostId = event.postId
                    )
                }
                getComments(event.postId)
            }

            HomeEvents.OnCommentsBottomSheetDismissed -> {
                //TODO: update count of added comment
                _uiStates.update {
                    it.copy(
                        commentsData = emptyList(), commentPostId = null
                    )
                }
            }

            HomeEvents.AddComment -> {
                val commentList = uiStates.value.commentsData.toMutableList()
                val data = uiStates.value.accountDetails?.run {
                    ShowCommentsModel(
                        _id = _id,
                        avatar = avatar,
                        fullName = fullName,
                        username = username,
                        content = comment
                    )
                }
                commentList.add(0, data!!)
                _uiStates.update {
                    it.copy(commentsData = commentList)
                }
                addComment(postId = uiStates.value.commentPostId!!, content = comment)
            }

            is HomeEvents.OnCommentValueChange -> {
                comment=event.comment
            }
        }

    }

    private fun likePost(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }

            likePostUseCase(postId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        withContext(Dispatchers.Main) {
                            _uiStates.update {
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
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound)
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest)
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
            _uiStates.update {
                it.copy(isLoading = true)
            }

            savePostUseCase(postId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        withContext(Dispatchers.Main) {
                            _uiStates.update {
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
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound)
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest)
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
                                it.copy(isLoading = false)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun followUser(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = getUserIdDataStoreUseCase()
            if (userId == accountId) {
                withContext(Dispatchers.Main) {
                    _uiStates.update {
                        it.copy(
                            errorMessage = UiText.StringResource(R.string.errorFollowSelf)
                        )
                    }
                }
                return@launch
            }
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

    private fun getComments(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isCommentsLoading = true)
            }
            getCommentsUseCase(postId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                            isCommentsLoading = false
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                            isCommentsLoading = false
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
                                    commentsData = result.data,
                                    isCommentsLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchUserIdDataStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = getUserIdDataStoreUseCase()
            withContext(Dispatchers.Main) {
                _uiStates.update {
                    it.copy(userId = userId)
                }
            }
            viewAccount(userId)
        }

    }

    private fun viewAccount(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }

            viewAccountUseCase(userId).collect { result ->
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

                            DataError.Network.NOT_FOUND -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikePostNotFound),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            DataError.Network.BAD_REQUEST -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorLikeBadRequest),
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
                                    accountDetails = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun addComment(postId: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {

            addCommentUseCase(postId, content).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        val commentList = uiStates.value.commentsData.toMutableList()
                        commentList.removeAt(0)
                        withContext(Dispatchers.Main) {
                            _uiStates.update {
                                it.copy(
                                    commentsData = commentList
                                )
                            }
                        }
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
                        comment=""
                        _uiStates.update {
                            it.copy(addedCommentCount = it.addedCommentCount+1)
                        }
                    }
                }
            }
        }
    }

}