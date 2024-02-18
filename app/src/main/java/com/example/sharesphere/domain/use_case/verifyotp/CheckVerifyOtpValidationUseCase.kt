package com.example.sharesphere.domain.use_case.verifyotp

import com.example.sharesphere.util.TextFieldValidation
import javax.inject.Inject

class CheckVerifyOtpValidationUseCase @Inject constructor(){
    operator fun invoke(emailOtp:String,mobileOtp:String):Boolean{
        return TextFieldValidation.isOtpValid(emailOtp)&&TextFieldValidation.isOtpValid(mobileOtp)
    }
}