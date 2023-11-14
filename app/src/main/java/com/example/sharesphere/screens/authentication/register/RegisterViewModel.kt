package com.example.sharesphere.screens.authentication.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.models.SignupResponse
import com.example.sharesphere.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val signupRepository: SignupRepository):ViewModel() {

    val signupResponse: StateFlow<SignupResponse>
        get() = signupRepository.signupResponse


    fun signup(email:String, password:String){
       viewModelScope.launch {
           signupRepository.signup(email,password)
           Log.d("meow",signupResponse.toString())
       }
    }

}