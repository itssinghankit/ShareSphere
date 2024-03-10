package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.VerifyOtpModel
import com.example.sharesphere.domain.use_case.mobile.MobileSendOtpUseCase
import com.example.sharesphere.domain.use_case.verifyotp.CheckVerifyOtpValidationUseCase
import com.example.sharesphere.domain.use_case.verifyotp.GetMobileDataStoreUseCase
import com.example.sharesphere.domain.use_case.verifyotp.SaveVerifyOtpDataStoreUseCase
import com.example.sharesphere.domain.use_case.verifyotp.VerifyOtpUseCase
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val checkVerifyOtpValidationUseCase: CheckVerifyOtpValidationUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val saveVerifyOtpDataStoreUseCase: SaveVerifyOtpDataStoreUseCase,
    private val mobileSendOtpUseCase: MobileSendOtpUseCase,
    private val getMobileDataStoreUseCase: GetMobileDataStoreUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyOtpStates())
    val uiState: StateFlow<VerifyOtpStates> = _uiState

    var textFieldState = mutableStateOf(VerifyOtpTextFieldStates())
        private set

    val networkState = networkMonitor.networkState


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: VerifyOtpEvents) {
        when (event) {
            is VerifyOtpEvents.onEmailOtpValueChange -> {
                textFieldState.value = textFieldState.value.copy(emailOtp = event.emailOtp)

                _uiState.update {

                    //as it is shown on screen and there is no asynchronous task so no need of coroutine
                    if (event.emailOtp.length == 6) {
                        it.copy(
                            isInputsValidated = checkVerifyOtpValidationUseCase(
                                textFieldState.value.emailOtp,
                                textFieldState.value.mobileOtp
                            )
                        )
                    } else {
                        it.copy(
                            isInputsValidated = false
                        )
                    }

                }

            }

            is VerifyOtpEvents.onMobileOtpValueChange -> {
                textFieldState.value = textFieldState.value.copy(mobileOtp = event.mobileOtp)

                _uiState.update {
                    if (event.mobileOtp.length == 6) {
                        it.copy(
                            isInputsValidated = checkVerifyOtpValidationUseCase(
                                textFieldState.value.emailOtp,
                                textFieldState.value.mobileOtp
                            )
                        )
                    } else {
                        it.copy(
                            isInputsValidated = false
                        )
                    }
                }

            }

            is VerifyOtpEvents.onSnackBarShown -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(errorMessage = null)
                    }
                }
            }

            is VerifyOtpEvents.onNextClicked -> {

                verifyOtpNetWorkRequest(
                    emailOtp = textFieldState.value.emailOtp,
                    mobileOtp = textFieldState.value.mobileOtp
                )
            }

            is VerifyOtpEvents.onResendClicked -> {
                viewModelScope.launch {
                    val mobile=getMobileDataStoreUseCase().first()
                    sendOtp(mobile?:"")
                }
                
            }

            is VerifyOtpEvents.onNavigationDone -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(navigate = false)
                    }
                }
            }
        }

    }

    private fun verifyOtpNetWorkRequest(emailOtp: String, mobileOtp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            verifyOtpUseCase(emailOtp, mobileOtp).collect { result ->
                when (result) {
                    is ApiResponse.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: UiText.DynamicString(""),
                                isLoading = false
                            )
                        }
                    }

                    is ApiResponse.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is ApiResponse.Success -> {
                        saveVerifyOtpDetailsDataStore(result.data!!)
                        _uiState.update {
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

    private fun saveVerifyOtpDetailsDataStore(data: VerifyOtpModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveVerifyOtpDataStoreUseCase(data)
        }
    }

    private fun sendOtp(mobile: String) {

        viewModelScope.launch(Dispatchers.IO) {
            mobileSendOtpUseCase(mobile).collect { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is ApiResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = UiText.StringResource(R.string.otp_sent_successfully),
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