package com.example.sharesphere.domain.use_case.user.common.userId

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserIdDataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): String {
        return dataStoreRepository.getString(PreferencesKeys.ID).first() ?: ""

    }
}