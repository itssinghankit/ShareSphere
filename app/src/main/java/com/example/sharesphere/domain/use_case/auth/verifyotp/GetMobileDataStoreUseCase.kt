package com.example.sharesphere.domain.use_case.auth.verifyotp

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMobileDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(): Flow<String?> {
        return dataStoreRepository.getString(PreferencesKeys.MOBILE)
    }

}