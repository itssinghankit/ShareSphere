package com.example.sharesphere.domain.repository

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import kotlinx.coroutines.flow.Flow

interface DataStoreRepositoryInterface {

    suspend fun get(preferencesKeys:PreferencesKeys): Flow<String?>
    suspend fun save(preferencesKeys:PreferencesKeys, value: String)

}