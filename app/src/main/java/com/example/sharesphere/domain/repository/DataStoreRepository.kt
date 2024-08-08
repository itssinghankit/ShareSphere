package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun getString(preferencesKeys:PreferencesKeys): Flow<String?>
    suspend fun getBoolean(preferencesKeys: PreferencesKeys):Flow<Boolean?>
    suspend fun getLong(preferencesKeys: PreferencesKeys):Flow<Long?>

    //overloading save function
    suspend fun save(preferencesKeys:PreferencesKeys, value: String)
    suspend fun save(preferencesKeys:PreferencesKeys, value: Boolean)
    suspend fun save(preferencesKeys:PreferencesKeys, value: Long)

    suspend fun deleteString(preferencesKeys: PreferencesKeys)

}