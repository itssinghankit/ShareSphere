package com.example.sharesphere.ui.screens.authentication.username

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sharesphere.R
import com.example.sharesphere.api.ApiResponse
import com.example.sharesphere.components.ComponentButton
import com.example.sharesphere.components.ComponentTextField
import com.example.sharesphere.helper.TextFieldValidation
import com.example.sharesphere.ui.theme.blacktxt
import com.example.sharesphere.ui.theme.orange
import com.example.sharesphere.ui.theme.orangebg


//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview() {
    UsernameScreen()
}
val TAG="meow"

@Composable
fun UsernameScreen() {
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var isUsernameValid by rememberSaveable {
        mutableStateOf(true)
    }

    val usernameViewModel:UsernameViewModel= hiltViewModel()
    val user=usernameViewModel.usernameResponseFlow.collectAsState()

    val context= LocalContext.current

    

    var errorMessage by rememberSaveable{
        mutableStateOf(context.getString(R.string.validateUsernameError))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg)
            .verticalScroll(rememberScrollState()), contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Image(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
            Text(
                text = "Create an username",
                color = orange,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.lato_black)),
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Must be minimum of length 3 with Lowercase Letters only",
                color = blacktxt,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lato_regular)),
                modifier = Modifier.padding(top = 8.dp)
            )
            ComponentTextField(
                label = "Username",
                modifier = Modifier.padding(top = 32.dp),
                value = username,
                onValueChange ={
                    username=it
                    isUsernameValid=TextFieldValidation.isUsernameValid(username)
                    Log.d(TAG, "UsernameScreen: $isUsernameValid")
                    if(isUsernameValid){
                        errorMessage= context.getString(R.string.usernameError)
                        usernameViewModel.username(it)
                    }else{
                        errorMessage=context.getString(R.string.validateUsernameError)
                    }


                               },
                leadingIconImageVector = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go, keyboardType = KeyboardType.Password),
                keyboardActions = KeyboardActions(onGo = {}),
                showError = !isUsernameValid,
                errorMessage = errorMessage,
            )
            when(user.value){
                is ApiResponse.Success->{
                    LaunchedEffect(key1 = isUsernameValid){
                        Log.d(TAG, "UsernameScreen1: $isUsernameValid")
                        isUsernameValid=TextFieldValidation.isUsernameValid(username)
                        if(isUsernameValid){
                            isUsernameValid=user.value.data?.data?.available!!
                        }

                    }
                    if(isUsernameValid){

                        ComponentButton(text = "Next", contColor = orange, txtColor = Color.White, modifier = Modifier.padding(top = 40.dp)) {
                            isUsernameValid=TextFieldValidation.isUsernameValid(username)
//                if(isUsernameValid){
//
//                }

                        }
                    }


                }
                is ApiResponse.Initial->{
                   LaunchedEffect(key1 = isUsernameValid){
                       Log.d(TAG, "UsernameScreen: load")
                       isUsernameValid=false
                   }
                }
                is ApiResponse.Error->{
                    LaunchedEffect(key1 = isUsernameValid){
                        Log.d(TAG, "UsernameScreen: load")
                        isUsernameValid=false
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }

                }
                is ApiResponse.Loading->{
                    LaunchedEffect(key1 = isUsernameValid){
                        Log.d(TAG, "UsernameScreen: load")
                        isUsernameValid=false
                    }
                }

            }
    }
}
}
