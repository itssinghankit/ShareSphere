package com.example.sharesphere.presentation.screens.authentication.verifyOtp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.verifyotp.CheckVerifyOtpValidationUseCase
import com.example.sharesphere.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val checkVerifyOtpValidationUseCase: CheckVerifyOtpValidationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyOtpStates())
    val uiState: StateFlow<VerifyOtpStates> = _uiState

    var textFieldState = mutableStateOf(VerifyOtpTextFieldStates())
        private set

    val networkState=networkMonitor.networkState

    fun onEvent(event: VerifyOtpEvents) {
        when (event) {
            is VerifyOtpEvents.onEmailOtpValueChange -> {
                textFieldState.value = textFieldState.value.copy(emailOtp = event.emailOtp)

                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            isInputsValidated = checkVerifyOtpValidationUseCase(
                                textFieldState.value.emailOtp,
                                textFieldState.value.mobileOtp
                            )
                        )
                    }
                }

            }

            is VerifyOtpEvents.onMobileOtpValueChange -> {
                textFieldState.value = textFieldState.value.copy(mobileOtp = event.mobileOtp)
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            isInputsValidated = checkVerifyOtpValidationUseCase(
                                textFieldState.value.emailOtp,
                                textFieldState.value.mobileOtp
                            )
                        )
                    }
                }

            }

            is VerifyOtpEvents.onSnackBarShown -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(showSnackBar = false)
                    }
                }
            }

            is VerifyOtpEvents.onNextClicked -> {
//                viewModelScope.launch {
//                    saveMobileDataStoreUseCase(textFieldState.value.mobile)
//                }
            }
            is VerifyOtpEvents.onResendClicked->{

            }
        }

    }
}