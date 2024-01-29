package com.example.sharesphere.ui.screens.authentication.username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.api.ApiResponse
import com.example.sharesphere.models.checkUsername.CheckUsernameResponse
import com.example.sharesphere.repository.UsernameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor(private val usernameRepository: UsernameRepository) :
    ViewModel() {

    val usernameResponseFlow: StateFlow<ApiResponse<CheckUsernameResponse>>
        get() = usernameRepository.usernameResponseFlow

    fun username(username: String) {
        viewModelScope.launch {
            usernameRepository.username(username)
        }
    }

}