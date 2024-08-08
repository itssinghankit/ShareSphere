package com.example.sharesphere.domain.use_case.auth.username

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveUsernameDatastoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend operator fun invoke(username: String) {
        dataStoreRepository.save(PreferencesKeys.USERNAME, username)
    }

}