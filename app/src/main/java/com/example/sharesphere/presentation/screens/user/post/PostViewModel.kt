package com.example.sharesphere.presentation.screens.user.post

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.use_case.user.post.CreatePostUseCase
import com.example.sharesphere.domain.use_case.user.post.ValidateCaptionUseCase
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

data class PostStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val postImages: List<Uri> = emptyList(),
    val isCaptionError: Boolean = false,
    val captionError: UiText? = null
)

@HiltViewModel
class PostViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    val validateCaptionUseCase: ValidateCaptionUseCase,
    val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _uiStates = MutableStateFlow(PostStates())
    val uiStates: StateFlow<PostStates> = _uiStates.asStateFlow()

    var caption by mutableStateOf("")
        private set

    val networkState = networkMonitor.networkState
    var runOnce= true

    fun onEvent(event: PostEvents) {
        when (event) {
            PostEvents.ResetErrorMessage -> {
                _uiStates.value = _uiStates.value.copy(errorMessage = null)
            }

            is PostEvents.OnCaptionTextChanged -> {
                caption = event.caption
                validateCaption(caption)
            }

            PostEvents.ResetPost -> {
                _uiStates.update {
                    it.copy(
                        postImages = emptyList()
                    )
                }
                caption = ""
            }

            is PostEvents.OnImagesSelected -> {
                _uiStates.update {
                    it.copy(postImages = event.uris)
                }
            }

            is PostEvents.SetError -> {
                _uiStates.update {
                    it.copy(
                        errorMessage = event.errorMessage
                    )
                }
            }

            PostEvents.OnPostClicked -> {
                if (caption.isNotEmpty() && !uiStates.value.isCaptionError && !_uiStates.value.isLoading) {
                    if (_uiStates.value.postImages.isNotEmpty()) {
                       if(runOnce){
                           runOnce=false
                           createPost()

                       }
                    } else {
                        _uiStates.update {
                            it.copy(
                                errorMessage = UiText.StringResource(R.string.validate_images_empty_error)
                            )
                        }
                    }

                } else {
                    validateCaption(caption)
                }
            }
        }
    }

    private fun validateCaption(caption: String) {
        when (val result = validateCaptionUseCase(caption)) {
            is ApiResult.Error -> {
                when (result.error) {
                    DataError.Local.EMPTY -> {
                        _uiStates.update {
                            it.copy(
                                isCaptionError = true,
                                captionError = UiText.StringResource(R.string.validate_caption_not_empty_error)
                            )
                        }
                    }

                    DataError.Local.INCORRECT_LENGTH -> {
                        _uiStates.update {
                            it.copy(
                                isCaptionError = true,
                                captionError = UiText.StringResource(R.string.validate_caption_length_error)
                            )
                        }
                    }

                    else -> {
                        _uiStates.update {
                            it.copy(
                                isCaptionError = true,
                                captionError = UiText.DynamicString("Caption is not valid")
                            )
                        }
                    }
                }
            }

            is ApiResult.Success -> {
                _uiStates.update {
                    it.copy(
                        isCaptionError = false,
                        captionError = null
                    )
                }
            }
        }
    }

    private fun createPost() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }

            createPostUseCase(
                caption = caption,
                postImages = _uiStates.value.postImages
            ).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.PAYLOAD_TOO_LARGE -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorPayloadTooLarge),
                                            isLoading = false
                                        )
                                    }
                                }
                            }
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
                            onEvent(PostEvents.ResetPost)
                            runOnce=true
                            _uiStates.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = UiText.DynamicString("Post Uploaded Successfully")
                                )
                            }
                        }

                    }
                }
            }
        }
    }


}