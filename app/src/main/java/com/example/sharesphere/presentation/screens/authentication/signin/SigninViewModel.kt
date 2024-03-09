package com.example.sharesphere.presentation.screens.authentication.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.model.SignInModel
import com.example.sharesphere.domain.use_case.signin.EmailValidationUseCase
import com.example.sharesphere.domain.use_case.signin.PasswordValidationUseCase
import com.example.sharesphere.domain.use_case.signin.SaveUserDataStoreUseCase
import com.example.sharesphere.domain.use_case.signin.SignInUseCase
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
class SigninViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val signInUseCase: SignInUseCase,
    private val saveUserDataStoreUseCase: SaveUserDataStoreUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInStates())
    val uiState: StateFlow<SignInStates> = _uiState

    var textFieldStates = mutableStateOf(SignInTextFieldStates())
        private set

    val networkState = networkMonitor.networkState

    private var validationJob: Job? = null

    fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnEmailValueChange -> {
                textFieldStates.value = textFieldStates.value.copy(email = event.email)

                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    _uiState.update {
                        withContext(Dispatchers.Main) {
                            it.copy(
                                isEmailError = !emailValidationUseCase(event.email)
                            )
                        }
                    }
                }
            }

            is SignInEvents.OnPasswordValueChange -> {
                textFieldStates.value = textFieldStates.value.copy(password = event.password)

                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    _uiState.update {
                        withContext(Dispatchers.Main) {
                            it.copy(
                                isPasswordError = !passwordValidationUseCase(event.password)
                            )
                        }
                    }
                }
            }

            SignInEvents.OnNavigationDone -> {
                //not adding viewModelScope because state is to be updated in main tread
                _uiState.update {
                    it.copy(navigateToHomeScreen = false, navigateToForgetPasswordScreen = false)
                }
            }

            SignInEvents.OnNextClick -> {
                signInNetworkRequest(textFieldStates.value.email, textFieldStates.value.password)
            }

            SignInEvents.OnSnackBarShown -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }

            }

            SignInEvents.OnForgetPasswordClicked -> {
                _uiState.update {
                    it.copy(navigateToForgetPasswordScreen = true)
                }
            }
        }
    }

    private fun signInNetworkRequest(email: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
            signInUseCase(usernameOrEmail = email, password = password).collect { result ->

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
                        saveUserToDataStore(details = result.data!!)
                        _uiState.update {
                            it.copy(
                                navigateToHomeScreen = true,
                                isLoading = false,
                            )
                        }
                    }

                }

            }
        }

    }

    private fun saveUserToDataStore(details: SignInModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserDataStoreUseCase(details)
        }
    }


}