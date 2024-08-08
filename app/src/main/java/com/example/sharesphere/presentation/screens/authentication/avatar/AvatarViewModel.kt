package com.example.sharesphere.presentation.screens.authentication.avatar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.model.auth.AvatarModel
import com.example.sharesphere.domain.use_case.auth.avatar.AvatarDetailsUploadUseCase
import com.example.sharesphere.domain.use_case.auth.avatar.BioValidationUseCase
import com.example.sharesphere.domain.use_case.auth.avatar.SaveAvatarDataStoreUseCase
import com.example.sharesphere.util.ApiResponse
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

@HiltViewModel
class AvatarViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val savedStateHandle: SavedStateHandle,
    private val avatarDetailsUploadUseCase: AvatarDetailsUploadUseCase,
    private val bioValidationUseCase: BioValidationUseCase,
    private val saveAvatarDataStoreUseCase: SaveAvatarDataStoreUseCase
) : ViewModel() {

    val networkState = networkMonitor.networkState

    private val _uiState = MutableStateFlow(AvatarStates())
    val uiState: StateFlow<AvatarStates> = _uiState

    var textFieldStates by mutableStateOf(AvatarTextFieldState())
        private set


    fun onEvent(event: AvatarEvents) {
        when (event) {
            is AvatarEvents.OnNextClicked -> {

                val fullName = savedStateHandle.get<String>("fullName")
                val dob = savedStateHandle.get<Long>("dob")
                val gender = savedStateHandle.get<String>("gender")

                detailsNetworkRequest(fullName, dob, gender)

            }

            is AvatarEvents.OnBioValueChanged -> {

                textFieldStates = textFieldStates.copy(
                    bio = event.bio
                )

                //validate bio
                _uiState.update {
                    it.copy(
                        isBioError = !bioValidationUseCase(event.bio)
                    )
                }
            }

            AvatarEvents.OnNavigationDone -> {
                _uiState.update {
                    it.copy(
                        navigate = false
                    )
                }
            }

            AvatarEvents.OnSnackBarShown -> {

                _uiState.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }

            is AvatarEvents.OnImageSelected -> {
                _uiState.update {
                    it.copy(avatar = event.avatar)
                }
            }
        }
    }

    private fun detailsNetworkRequest(fullName: String?, dob: Long?, gender: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            avatarDetailsUploadUseCase(
                _uiState.value.avatar!!,
                fullName!!,
                dob!!,
                gender!!,
                textFieldStates.bio
            ).collect { result ->
                when (result) {
                    is ApiResponse.Error -> {
                        _uiState.update {
                            withContext(Dispatchers.Main) {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = result.message ?: UiText.DynamicString("")
                                )
                            }
                        }
                    }

                    is ApiResponse.Loading -> {
                        _uiState.update {
                            withContext(Dispatchers.Main) {
                                it.copy(isLoading = true)
                            }
                        }
                    }

                    is ApiResponse.Success -> {
                        //save the data and updating ui
                        saveDetails(result.data!!)
                        _uiState.update {
                            withContext(Dispatchers.Main) {
                                it.copy(
                                    isLoading = false,
                                    navigate = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveDetails(data: AvatarModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveAvatarDataStoreUseCase(data)
        }
    }

}