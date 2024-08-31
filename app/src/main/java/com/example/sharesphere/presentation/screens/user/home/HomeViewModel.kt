package com.example.sharesphere.presentation.screens.user.home

import androidx.lifecycle.ViewModel
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val data: Any? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
): ViewModel() {

    private val _uiState= MutableStateFlow(HomeStates())
    val uiState:StateFlow<HomeStates> = _uiState

    val networkState = networkMonitor.networkState

    fun onEvent(event:HomeEvents){
        when(event){
            HomeEvents.ResetErrorMessage -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }
        }

    }

}