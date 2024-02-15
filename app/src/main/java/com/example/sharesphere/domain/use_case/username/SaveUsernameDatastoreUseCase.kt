package com.example.sharesphere.domain.use_case.username

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveUsernameDatastoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {

    suspend operator fun invoke(username: String) {
        dataStoreRepositoryInterface.save(PreferencesKeys.Username, username)
    }

}