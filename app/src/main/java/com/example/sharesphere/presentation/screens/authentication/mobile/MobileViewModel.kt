package com.example.sharesphere.presentation.screens.authentication.mobile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.mobile.MobileSendOtpUseCase
import com.example.sharesphere.domain.use_case.mobile.MobileValidationUseCase
import com.example.sharesphere.domain.use_case.mobile.SaveMobileDataStoreUseCase
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val mobileValidationUseCase: MobileValidationUseCase,
    private val saveMobileDataStoreUseCase: SaveMobileDataStoreUseCase,
    private val mobileSendOtpUseCase: MobileSendOtpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MobileStates())
    val uiState: StateFlow<MobileStates> = _uiState

    var textFieldState = mutableStateOf(MobileTextFieldStates())
        private set

    val networkState = networkMonitor.networkState

    //for not validating again and again with each typed character
    private var validationJob: Job? = null

    fun onEvent(event: MobileEvents) {
        when (event) {
            is MobileEvents.MobileOnValueChange -> {
                textFieldState.value = textFieldState.value.copy(mobile = event.mobile)

                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {

                    delay(500L)
                    withContext(Dispatchers.Main){
                        _uiState.update {
                            it.copy(isMobileError = !mobileValidationUseCase(event.mobile))
                        }
                    }

                }
            }

            is MobileEvents.SnackBarShown -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(errorMessage = null)
                    }
                }
            }

            is MobileEvents.NextClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveMobileDataStoreUseCase(textFieldState.value.mobile)
                    sendOtp(textFieldState.value.mobile)
                }
            }

            is MobileEvents.onNavigationDone -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(navigate = false)
                    }
                }
            }
        }

    }


    private fun sendOtp(mobile: String) {

        viewModelScope.launch(Dispatchers.IO) {
            mobileSendOtpUseCase(mobile).collect { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true, isMobileError = false)
                        }
                    }

                    is ApiResponse.Success -> {
                        _uiState.update {
//                            val success = result.data?.success ?: false
                            it.copy(
                                navigate = true,
                                isLoading = false,
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: UiText.DynamicString(""),
                                isLoading = false
                            )
                        }
                    }

                }
            }
        }
    }

}