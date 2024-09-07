package com.example.sharesphere.util

import android.util.Patterns

object TextFieldValidation {
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isUsernameValid(username:String):Boolean{
        val usernameRegex="\\w{3,}".toRegex()
        return usernameRegex.matches(username)
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&*=]).{8,}".toRegex()
        return passwordRegex.matches(password)
    }

    fun isBothPasswordSame(password1: String,password2: String):Boolean{
        return password1==password2
    }
     fun isMobileValid(mobile:String):Boolean{
         val usernameRegex="[9,8,7,6]\\d{9}".toRegex()
         return usernameRegex.matches(mobile)
     }

    fun isOtpValid(otp:String):Boolean{
        return otp.length==6
    }

    fun isFullNameValid(fullName:String):Boolean{
        val regex="^[a-zA-Z]+[ a-zA-Z]{2,20}$".toRegex()
        return regex.matches(fullName)
    }

    fun isBioValid(bio:String):Boolean{
       return bio.length in 3..100
    }
    fun isCaptionValid(caption:String):Boolean{
        return caption.length in 3..100
    }

}