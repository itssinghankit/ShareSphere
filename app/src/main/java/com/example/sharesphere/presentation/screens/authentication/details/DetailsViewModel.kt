package com.example.sharesphere.presentation.screens.authentication.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.details.FullNameValidationUseCase
import com.example.sharesphere.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val fullNameValidationUseCase: FullNameValidationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsStates())
    val uiState: StateFlow<DetailsStates> = _uiState

    var textFieldStates by mutableStateOf(DetailsTextFieldStates())
        private set

    val networkState = networkMonitor.networkState

    fun onEvent(event: DetailsEvents) {
        when (event) {
            is DetailsEvents.OnFullNameValueChange -> {
                textFieldStates = textFieldStates.copy(
                    fullName = event.fullName
                )

                _uiState.update {
                    it.copy(isFullNameError = !fullNameValidationUseCase(event.fullName))
                }

            }

            DetailsEvents.OnNavigationDone -> {

                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(
                            navigate = false
                        )
                    }
                }

            }

            DetailsEvents.OnSnackBarShown -> {

                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }

            is DetailsEvents.OnGenderSelected -> {

                _uiState.update {
                    it.copy(gender = event.gender)
                }
            }

//            is DetailsEvents.OnDateChanged -> {
//                Timber.d(event.date.toString())
//                _uiState.update {
//                    it.copy(date = event.date)
//                }
//            }
            is DetailsEvents.OnNextClicked -> {
                _uiState.update {
                    it.copy(navigate = true)
                }

            }
        }
    }

}