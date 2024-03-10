package com.example.sharesphere.domain.use_case.verifyotp

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMobileDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {

    suspend operator fun invoke(): Flow<String?> {
        return dataStoreRepositoryInterface.getString(PreferencesKeys.Mobile)
    }

}