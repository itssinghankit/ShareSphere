package com.example.sharesphere.helper

import android.util.Patterns

object TextFieldValidation {
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&*=]).{8,}".toRegex()
        return passwordRegex.matches(password)
    }

    fun isBothPasswordSame(password1: String,password2: String):Boolean{
        return password1==password2
    }

    
}