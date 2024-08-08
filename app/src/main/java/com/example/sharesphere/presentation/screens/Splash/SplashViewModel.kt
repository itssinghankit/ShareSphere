package com.example.sharesphere.presentation.screens.Splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.domain.use_case.splash.GetAuthDetailsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class SplashState(
    val isSignedUp: Boolean = false,
    val isVerified: Boolean = false,
    val isDetailsFilled: Boolean = false,
    val navigate: Boolean = false
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthDetailsDataStore: GetAuthDetailsDataStore
) : ViewModel() {

    init {
        getAuthDetails()
    }

    var state by mutableStateOf(SplashState())
        private set

    private fun getAuthDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAuthDetailsDataStore()
           withContext(Dispatchers.Main){
               state = state.copy(
                   isSignedUp = result.isSignedUp,
                   isVerified = result.isVerified,
                   isDetailsFilled = result.isDetailsFilled,
                   navigate = true
               )
           }
        }
    }

}