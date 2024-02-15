package com.example.sharesphere.domain.use_case.username

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsernameDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {

    suspend fun getUsernameDataStore(): Flow<String?> {
       return dataStoreRepositoryInterface.get(PreferencesKeys.Username)
    }

}