package com.example.sharesphere.screens.authentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.models.SigninResponse
import com.example.sharesphere.repository.SigninRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(private val signinRepository:SigninRepository):ViewModel() {

    val signinResponse:StateFlow<SigninResponse>
            get()=signinRepository.signinResponse

  fun signin(email:String,password:String){
       viewModelScope.launch {
           signinRepository.singin(email,password)
       }
    }

}