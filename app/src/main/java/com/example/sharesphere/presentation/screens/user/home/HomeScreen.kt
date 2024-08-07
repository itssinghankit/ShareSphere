package com.example.sharesphere.presentation.screens.user.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.sharesphere.data.repository.datastore.DataStoreRepositoryImplementation
import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun HomeScreen() {
//    val dataStoreRepositoryImplementation=DataStoreRepositoryImplementation(LocalContext.current)
//    var name=""
//    var dob=""
//    var email=""
//    var isdetailfilled=false
//    var mobile=0L
//    LaunchedEffect(Unit) {
//        name=dataStoreRepositoryImplementation.getString(PreferencesKeys.FullName).first() ?: ""
//        dob=dataStoreRepositoryImplementation.getString(PreferencesKeys.Dob).first()?:""
//        email=dataStoreRepositoryImplementation.getString(PreferencesKeys.Email).first()?:""
//        isdetailfilled=dataStoreRepositoryImplementation.getBoolean(PreferencesKeys.IsDetailsFilled).first()?:false
//        mobile=dataStoreRepositoryImplementation.getLong(PreferencesKeys.Mobile).first()?:1
//
//    }
    //TODO: signin details save  splash logic
    Surface {

        Column {

            Text(modifier=Modifier.fillMaxSize(),text = "welcome home ", textAlign = TextAlign.Center)
        }

    }
}