package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import kotlinx.coroutines.flow.Flow

interface DataStoreRepositoryInterface {

    suspend fun getString(preferencesKeys:PreferencesKeys): Flow<String?>
    suspend fun getBoolean(preferencesKeys: PreferencesKeys):Flow<Boolean?>

    //overloading save function
    suspend fun save(preferencesKeys:PreferencesKeys, value: String)
    suspend fun save(preferencesKeys:PreferencesKeys, value: Boolean)

}