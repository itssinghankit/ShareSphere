package com.example.sharesphere.presentation.authentication.username

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.common.ApiResponse
import com.example.sharesphere.domain.use_case.username.CheckAvailabilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
    var state by mutableStateOf(UsernameState())
        private set

    var ankit by mutableStateOf(UsernameState())
    //using mutable state slow when asynchronous call is to be made- maintain encapsulation
    //can use var state by mutableStateOf()- breaks encapsulation we can use private set with it to maintain encapsulation
    //private set will only allow the state to be changed within class itself maintaining encapsulation property

    private var serverJob: Job? = null

    //lateinit
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onEvent(event: UsernameEvents) {
        when (event) {
            is UsernameEvents.onNextClick -> {
                ankit = ankit.copy(error = "")

            }

            is UsernameEvents.onValueChange -> {
                state = state.copy(username = event.username)
                serverJob?.cancel()
                serverJob = viewModelScope.launch {
                    delay(500L)
                    checkUsername(state.username)
                }
            }
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun checkUsername(username: String) {
        viewModelScope.launch {
            checkAvailabilityUseCase(username).onEach { result ->
                when (result) {
                    is ApiResponse.Loading -> {
//                    _state.value = UsernameState(isLoading = false)
                        _state
                    }

                    is ApiResponse.Success -> {
                        _state.value = UsernameState(available = result.data?.available ?: false)
                    }

                    is ApiResponse.Error -> {
                        _state.value = UsernameState(error = result.message ?: "Something vm wrong")
                    }

                    is ApiResponse.Initial -> {
                        _state.value = UsernameState(isLoading = false)
                    }
                }
            }
        }
    }
//    val usernameResponseFlow: StateFlow<ApiResponse<CheckUsernameResponseDto>>
//        get() = usernameRepository.usernameResponseFlow

//    fun username(username: String) {
//        viewModelScope.launch {
//            usernameRepository.username(username)
//        }
//    }

}