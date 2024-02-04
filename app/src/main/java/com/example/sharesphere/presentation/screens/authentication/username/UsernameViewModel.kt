package com.example.sharesphere.presentation.screens.authentication.username

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.R
import com.example.sharesphere.util.ApiResponse
import com.example.sharesphere.domain.use_case.username.CheckAvailabilityUseCase
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
class UsernameViewModel @Inject constructor(private val checkAvailabilityUseCase: CheckAvailabilityUseCase) :
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

            }

            is UsernameEvents.onValueChange -> {
                //update the username state
                username = event.username
                _uiState.update {
                    it.copy(available = false)
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
            Timber.d("call3")

            checkAvailabilityUseCase(username).collect{ result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        _uiState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is ApiResponse.Success -> {
                        _uiState.update {
                            //if username is not available=false, isError=true
                            var isError=false
                            if(result.data?.available == false){
                                isError=true
                            }
                            it.copy(available = result.data?.available ?: false, isLoading = false, isError = isError, error=UiText.DynamicString(result.data?.message?:""))
                        }
                    }

                    is ApiResponse.Error -> {
                        _uiState.update {
                            it.copy(error = result.message ?: UiText.DynamicString(""), isError = true, isLoading = false, available = false)
                        }
                    }
                }
            }
        }
    }
}