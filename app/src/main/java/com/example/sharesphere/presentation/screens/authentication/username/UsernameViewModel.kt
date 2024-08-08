package com.example.sharesphere.presentation.screens.authentication.username

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.use_case.auth.username.CheckAvailabilityUseCase
import com.example.sharesphere.domain.use_case.auth.username.SaveUsernameDatastoreUseCase
import com.example.sharesphere.domain.use_case.auth.username.UsernameValidationUseCase
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val checkAvailabilityUseCase: CheckAvailabilityUseCase,
    private val usernameValidationUseCase: UsernameValidationUseCase,
    private val saveUsernameDatastoreUseCase: SaveUsernameDatastoreUseCase
) :
    ViewModel() {

    /*
    // Game UI state
    //instead of having a mutable and immutable variable we can simply use private set with one variable
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    //_uiState.update{a->a.copy()} can only be used with flow

    //userGuess is TextField so can't use any reactive stream asynchronous method like flow
    //otherwise it create lag between typing typically we should ASAP assign value to textField state
    var userGuess by mutableStateOf("")
    private set

    //state is set only once but Stateflow can be set again and again using collect and emit
    //Asynchronous-mutableStateFlow
    //simple compose state-mutableState
    */
    //we will not use mutableStateFlow because for eac textField we need to define mutableState("") separately
//    private var _uiState = MutableStateFlow(UsernameState())
//    val uiState: StateFlow<UsernameState> = _uiState.asStateFlow()

    //    private val _state = mutableStateOf(UsernameState())
//    val state: State<UsernameState> = _state

    //using mutable state slow when asynchronous call is to be made- maintain encapsulation
    //can use var state by mutableStateOf()- breaks encapsulation we can use private set with it to maintain encapsulation
    //private set will only allow the state to be changed within class itself maintaining encapsulation property


    //Best practice
    private val _uiState = MutableStateFlow(UsernameState())
    val uiState: StateFlow<UsernameState> = _uiState.asStateFlow()

    var textFieldState = mutableStateOf(UsernameTextFieldState())
        private set

    private var serverJob: Job? = null

    val networkState = networkMonitor.networkState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: UsernameEvents) {
        when (event) {
            is UsernameEvents.OnNextClick -> {

                //TODO : remove datastore class use case
                //to save username in datastore
                viewModelScope.launch {
                    saveUsernameDatastoreUseCase(textFieldState.value.username)
                }

                //we can also use event.username which pass the username from compose screen but it
                //is not good to do as it first take username from viewmodel and then again return
                //it to this event present in viewmodel, so it not make any sense to rotate the data
            }

            is UsernameEvents.SnackBarShown -> {
                viewModelScope.launch {
                    delay(2000L)
                    _uiState.update {
                        it.copy(textFieldErrorMessage = null)
                    }
                }
            }

            is UsernameEvents.OnUsernameValueChange -> {
                //update the username state
                textFieldState.value = textFieldState.value.copy(username = event.username)

                _uiState.update {
                    it.copy(isAvailable = false)
                }

                //if user type quickly then validation and network call is not done frequently
                serverJob?.cancel()
                serverJob = viewModelScope.launch {
                    delay(500L)
                    checkUsername(textFieldState.value.username.lowercase())
                }
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun checkUsername(username: String) {
        viewModelScope.launch {

            if (usernameValidationUseCase(username)) {
                checkAvailabilityUseCase(username).collect { result ->
                    when (result) {
                        is ApiResponse.Loading -> {
                            _uiState.update { currentState ->
                                currentState.copy(isLoading = true, isUsernameError = false)
                            }
                        }

                        is ApiResponse.Success -> {
                            _uiState.update {
                                //if username is not available=false, isError=true
                                var isTextFieldError = false
                                if (result.data?.available == false) {
                                    isTextFieldError = true
                                }
                                it.copy(
                                    isAvailable = result.data?.available ?: false,
                                    isLoading = false,
                                    isUsernameError = isTextFieldError,
                                    textFieldErrorMessage = UiText.DynamicString(
                                        result.data?.message ?: ""
                                    )
                                )
                            }
                        }

                        is ApiResponse.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.message ?: UiText.DynamicString(""),
                                    isLoading = false,
                                    isAvailable = false
                                )
                            }
                        }
                    }
                }

            } else {
                _uiState.update {
                    it.copy(
                        isUsernameError = true, textFieldErrorMessage = UiText.StringResource(
                            R.string.validateUsernameError
                        )
                    )
                }
            }

        }
    }
}