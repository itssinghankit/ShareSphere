package com.example.sharesphere.presentation.screens.user.profile

import androidx.lifecycle.ViewModel
import com.example.sharesphere.di.NetworkModule
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ProfileStates(
    val errorMessage: UiText? = null,
    val isLoading: Boolean=false,

)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiStates = MutableStateFlow(ProfileStates())
    val uiState:StateFlow<ProfileStates> = _uiStates.asStateFlow()

    val networkState = networkMonitor.networkState

    fun onEvent(event:ProfileEvents){
        when(event){
            ProfileEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(errorMessage = null)
                }
            }
        }
    }


}