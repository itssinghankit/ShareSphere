//package com.example.sharesphere.domain.newstructure
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//
//@HiltViewModel
//class AuthViewMod(
//    private val authRepo:AuthRepo
//): ViewModel()
//{
//    fun loginClick(password:String){
//        when(val result=TextValidator().validatePassword(password)){
//            is ApiResult2.Error -> {
//                when(result.error){
//                    TextValidator.PasswordError.TOO_SHORT -> {
//
//                        //message too short
//                    }
//                    TextValidator.PasswordError.TOO_LONG -> {
//                        //message too long
//                    }
//                }
//            }
//            is ApiResult2.Success -> {
//               functionCall(password)
//            }
//        }
//    }
//    private fun functionCall(password: String) {
//        viewModelScope.launch {
//            when(val result=authRepo.login(password)){
//                is ApiResult2.Error -> {
//                    when(result.error){
//                        DataError.Network.NO_INTERNET -> TODO()
//                        DataError.Network.INTERNAL_SERVER_ERROR -> TODO()
//                        DataError.Network.NOT_FOUND -> TODO()
//                        else -> {
//
//                        }
//                    }
//                }
//                is ApiResult2.Success -> {
//                    //success wow
//                    result.data.password
//                }
//            }
//        }
//    }
//}
//
