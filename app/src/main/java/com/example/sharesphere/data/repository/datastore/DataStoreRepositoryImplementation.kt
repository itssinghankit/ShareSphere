package com.example.sharesphere.data.repository.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImplementation @Inject constructor(private val context: Context) :
    DataStoreRepositoryInterface {

    private val Context.datastore by preferencesDataStore("datastore")

    override suspend fun getString(preferencesKeys: PreferencesKeys): Flow<String?> =
        context.datastore.data.catch {
            if (this is Exception) {
                emit(emptyPreferences())

            }
        }.map { preference ->
            val datastoreKey = stringPreferencesKey(preferencesKeys.key)
            preference[datastoreKey] ?: ""
        }

    override suspend fun getBoolean(preferencesKeys: PreferencesKeys): Flow<Boolean?> =
        context.datastore.data.catch {
            if (this is Exception) {
                emit(emptyPreferences())

            }
        }.map { preference ->
            val datastoreKey = booleanPreferencesKey(preferencesKeys.key)
            preference[datastoreKey] ?: false
        }

    override suspend fun save(preferencesKeys: PreferencesKeys, value: String) {
        val datastoreKey = stringPreferencesKey(preferencesKeys.key)
        context.datastore.edit { preference ->
            preference[datastoreKey] = value
        }
    }

    override suspend fun save(preferencesKeys: PreferencesKeys, value: Boolean) {
        val datastoreKey = booleanPreferencesKey(preferencesKeys.key)
        context.datastore.edit { preference ->
            preference[datastoreKey] = value
        }
    }

}


