package com.example.sharesphere.presentation.screens.authentication.mobile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.mobile.MobileValidationUseCase
import com.example.sharesphere.domain.use_case.mobile.SaveMobileDataStoreUseCase
import com.example.sharesphere.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val mobileValidationUseCase: MobileValidationUseCase,
    private val saveMobileDataStoreUseCase: SaveMobileDataStoreUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MobileStates())
    val uiState: StateFlow<MobileStates> = _uiState

    var textFieldState = mutableStateOf(MobileTextFieldStates())
        private set

    val networkState = networkMonitor.networkState

    fun onEvent(event: MobileEvents) {
        when (event) {
            is MobileEvents.MobileOnValueChange -> {
                textFieldState.value = textFieldState.value.copy(mobile = event.mobile)

                viewModelScope.launch {
                    _uiState.update {
                        it.copy(isMobileError = !mobileValidationUseCase(event.mobile))
                    }
                }

            }

            is MobileEvents.SnackBarShown -> {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(showSnackBar = false)
                    }
                }
            }

            is MobileEvents.NextClicked -> {
                viewModelScope.launch {
                saveMobileDataStoreUseCase(textFieldState.value.mobile)
                }
            }
        }

    }
}