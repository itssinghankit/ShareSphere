package com.example.sharesphere.presentation.screens.authentication.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.register.CPasswordValidationUseCase
import com.example.sharesphere.domain.use_case.register.EmailValidationUseCase
import com.example.sharesphere.domain.use_case.register.PasswordValidationUseCase
import com.example.sharesphere.domain.use_case.register.SaveRegisterDataStoreUseCase
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
class RegisterViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val cPasswordValidationUseCase: CPasswordValidationUseCase,
    private val saveRegisterDataStoreUseCase: SaveRegisterDataStoreUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterStates())
    val uiState: StateFlow<RegisterStates> = _uiState

    //textfield states
    var textFieldStates = mutableStateOf(RegisterTextFieldStates())
        private set

    val networkState = networkMonitor.networkState


    fun onEvents(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.OnEmailValueChange -> {
                Timber.d("email section ${event.email}")
                val updatedEmail = event.email
                textFieldStates.value = textFieldStates.value.copy(email = updatedEmail)

                //email validation
                _uiState.update {
                    it.copy(isEmailError = !emailValidationUseCase(email = updatedEmail))
                }

            }

            is RegisterEvents.OnPasswordValueChange -> {

                val updatedPassword = event.password
                val updatedCPassword = textFieldStates.value.cPassword
                textFieldStates.value = textFieldStates.value.copy(password = updatedPassword)

                //email validation
                _uiState.update {
                    it.copy(
                        isPasswordError = !passwordValidationUseCase(updatedPassword),
                        isCPasswordError = !cPasswordValidationUseCase(
                            updatedPassword,
                            updatedCPassword
                        )
                    )
                }

            }

            is RegisterEvents.OnCPasswordValueChange -> {

                val updatedPassword = textFieldStates.value.password
                val updatedCPassword = event.cPassword
                textFieldStates.value = textFieldStates.value.copy(cPassword = updatedCPassword)

                //email validation
                _uiState.update {
                    it.copy(
                        isCPasswordError = !cPasswordValidationUseCase(
                            updatedPassword,
                            updatedCPassword
                        )
                    )
                }

            }

            is RegisterEvents.OnNextClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveRegisterDataStoreUseCase(
                        textFieldStates.value.email,
                        textFieldStates.value.password
                    )
                    //TODO : do the navigate state logic
                    _uiState.update {
                        it.copy(navigate = true)
                    }
                }
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

//    val signupResponseFlow: StateFlow<ApiResponse<SignupResponse>>
//        get() = signupRepository.signupResponseFlow
//
//
//    fun signup(email: String, password: String) {
//        viewModelScope.launch {
//            signupRepository.signup(email, password)
//        }
//    }

}