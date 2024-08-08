package com.example.sharesphere.domain.use_case.auth.username

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsernameDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend fun getUsernameDataStore(): Flow<String?> {
       return dataStoreRepository.getString(PreferencesKeys.USERNAME)
    }

}