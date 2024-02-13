package com.example.sharesphere.presentation.screens.authentication.username

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.domain.use_case.username.CheckAvailabilityUseCase
import com.example.sharesphere.domain.use_case.username.UsernameValidationUseCase
import com.example.sharesphere.presentation.navigation.NavigationActions
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.util.TextFieldValidation
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(
    private val checkAvailabilityUseCase: CheckAvailabilityUseCase,
    private val usernameValidationUseCase: UsernameValidationUseCase
) :
    ViewModel() {

    /*
    // Game UI state
    //instead of having a mutable and immutable variable we can simply use private set with one variablef
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    //_uiState.update{a->a.copy()} can only be used with flow

    //userGuess is TextField so can't use any reactive stream asynchronous method like flow
    //otherwise it create lag between typing typically we should ASAP assign value to textfeild state
    var userGuess by mutableStateOf("")
    private set

    //state is set only once but Stateflow can be set again and again using collect and emit
    //Asynchronous-mutableStateFlow
    //simple compose state-mutableState
    */
    //we will not use mutableStateFlow because for eac textfeild we need to define mutableState("") seperately
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

    //navigation flow
    private val _navigationEvents = mutableStateOf<NavigationActions?>(null)
    val navigationEvents: State<NavigationActions?> = _navigationEvents

    //textfeild states
    var username by mutableStateOf("")
        private set

    //lateinit
    private var serverJob: Job? = null

    val usernameHasLocalError by derivedStateOf {
        TextFieldValidation.isUsernameValid(username)
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: UsernameEvents) {
        when (event) {
            is UsernameEvents.onNextClick -> {
                //we can also use event.username which pass the username from compose screen but it
                //is not good to do as it first take username from viewmodel and thena again return
                //it to this event present in viewmodel, so it not make any sense to rotate the data
                Timber.d("hello3")
                _navigationEvents.value = NavigationActions.NavigateToSignin(event.username)
            }

            is UsernameEvents.snackbarShown -> {
                viewModelScope.launch {
                    delay(2000L)
                    _uiState.update {
                        it.copy(showSnackBar = false)
                    }
                }
            }

            is UsernameEvents.onValueChange -> {
                //update the username state
                username = event.username

                _uiState.update {
                    it.copy(isavailable = false)
                }

                Timber.d(event.username)

                //if user type quickly then validation and network call is not done frequently
                serverJob?.cancel()
                serverJob = viewModelScope.launch {
                    delay(500L)
                    checkUsername(username.lowercase())
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
                                currentState.copy(isLoading = true, isTextfieldError = false)
                            }
                        }

                        is ApiResponse.Success -> {
                            _uiState.update {
                                //if username is not available=false, isError=true
                                var isTextfieldError = false
                                if (result.data?.available == false) {
                                    isTextfieldError = true
                                }
                                it.copy(
                                    isavailable = result.data?.available ?: false,
                                    isLoading = false,
                                    isTextfieldError = isTextfieldError,
                                    textfieldErrorMessage = UiText.DynamicString(result.data?.message ?: "")
                                )
                            }
                        }

                        is ApiResponse.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.message ?: UiText.DynamicString(""),
                                    showSnackBar = true,
                                    isError = true,
                                    isLoading = false,
                                    isavailable = false
                                )
                            }
                        }
                    }
                }

            } else {
                _uiState.update {
                    it.copy(
                        isTextfieldError = true, textfieldErrorMessage = UiText.StringResource(
                            R.string.validateUsernameError
                        )
                    )
                }
            }

        }
    }
}