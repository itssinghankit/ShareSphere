package com.example.sharesphere.presentation.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.common.ApiResponse
import com.example.sharesphere.models.SignupResponse
import com.example.sharesphere.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val signupRepository: SignupRepository):ViewModel() {

    val signupResponseFlow: StateFlow<ApiResponse<SignupResponse>>
        get() = signupRepository.signupResponseFlow


    fun signup(email:String, password:String){
       viewModelScope.launch {
           signupRepository.signup(email,password)
       }
    }

}