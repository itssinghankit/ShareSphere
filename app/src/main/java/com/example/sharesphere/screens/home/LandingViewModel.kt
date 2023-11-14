package com.example.sharesphere.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(val signupRepository: SignupRepository):ViewModel() {

    fun vmsignup(email:String,password:String){
       viewModelScope.launch {
           signupRepository.signup(email,password)
       }
    }

}