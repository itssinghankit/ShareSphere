package com.example.sharesphere.domain.use_case.auth.mobile

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class MobileValidationUseCase @Inject constructor(){
    suspend operator fun invoke(mobile:String):Boolean{
        return TextFieldValidation.isMobileValid(mobile)
    }
}