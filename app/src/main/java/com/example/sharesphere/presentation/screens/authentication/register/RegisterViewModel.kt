package com.example.sharesphere.presentation.screens.authentication.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.model.auth.RegisterModel
import com.example.sharesphere.domain.use_case.auth.register.CPasswordValidationUseCase
import com.example.sharesphere.domain.use_case.auth.register.EmailValidationUseCase
import com.example.sharesphere.domain.use_case.auth.register.PasswordValidationUseCase
import com.example.sharesphere.domain.use_case.auth.register.RegisterUseCase
import com.example.sharesphere.domain.use_case.auth.register.SaveRegisterDataStoreUseCase
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
class RegisterViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val cPasswordValidationUseCase: CPasswordValidationUseCase,
    private val saveRegisterDataStoreUseCase: SaveRegisterDataStoreUseCase,
    private val registerUseCase: RegisterUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterStates())
    val uiState: StateFlow<RegisterStates> = _uiState

    var textFieldStates = mutableStateOf(RegisterTextFieldStates())
        private set

    val networkState = networkMonitor.networkState

    var validationJob: Job? = null


    fun onEvents(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.OnEmailValueChange -> {
                textFieldStates.value = textFieldStates.value.copy(email = event.email)

                //email validation
                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    _uiState.update {
                        it.copy(isEmailError = !emailValidationUseCase(email = event.email))
                    }
                }

            }

            is RegisterEvents.OnPasswordValueChange -> {

                val updatedPassword = event.password
                val updatedCPassword = textFieldStates.value.cPassword
                textFieldStates.value = textFieldStates.value.copy(password = updatedPassword)

                //passwords validation
                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    _uiState.update {
                        withContext(Dispatchers.Main) {
                            it.copy(
                                isPasswordError = !passwordValidationUseCase(updatedPassword),
                                isCPasswordError = !cPasswordValidationUseCase(
                                    updatedPassword,
                                    updatedCPassword
                                )
                            )
                        }

                    }
                }


            }

            is RegisterEvents.OnCPasswordValueChange -> {

                val updatedPassword = textFieldStates.value.password
                val updatedCPassword = event.cPassword
                textFieldStates.value = textFieldStates.value.copy(cPassword = updatedCPassword)

                //passwords validation
                validationJob?.cancel()
                validationJob = viewModelScope.launch(Dispatchers.IO) {
                    delay(500L)
                    _uiState.update {
                        withContext(Dispatchers.Main) {
                            it.copy(
                                isCPasswordError = !cPasswordValidationUseCase(
                                    updatedPassword,
                                    updatedCPassword
                                )
                            )
                        }

                    }
                }

            }

            is RegisterEvents.OnNextClick -> {

                //taking username from previous screen
                val username = savedStateHandle.get<String>("username") ?: ""
                registerNetworkRequest(
                    email = textFieldStates.value.email,
                    password = textFieldStates.value.password,
                    username = username
                )

            }

            is RegisterEvents.onSnackBarShown -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(errorMessage = null)
                    }
                }
            }

            is RegisterEvents.onNavigationDone -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update {
                        it.copy(navigate = false)
                    }
                }
            }
        }
    }

    private fun registerNetworkRequest(email: String, password: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUseCase(email, password, username).collect { result ->
                when (result) {
                    is ApiResponse.Error -> {
                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.message ?: UiText.DynamicString(""),
                                    isLoading = false
                                )
                            }
                        }
                    }

                    is ApiResponse.Loading -> {
                        withContext(Dispatchers.Main) {
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }

                    is ApiResponse.Success -> {
                        //save the date internally and update ui
                        saveRegisterDataStore(result.data!!)

                        withContext(Dispatchers.Main) {
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
    }

    private fun saveRegisterDataStore(data: RegisterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveRegisterDataStoreUseCase(data)
        }
    }

}