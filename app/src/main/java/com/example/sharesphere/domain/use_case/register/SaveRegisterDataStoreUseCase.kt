package com.example.sharesphere.domain.use_case.register

import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepositoryInterface
import javax.inject.Inject

class SaveRegisterDataStoreUseCase @Inject constructor(private val dataStoreRepositoryInterface: DataStoreRepositoryInterface) {
    suspend operator fun invoke(email:String,password:String){
        dataStoreRepositoryInterface.save(PreferencesKeys.Email,email)
        dataStoreRepositoryInterface.save(PreferencesKeys.Password,password)
    }
}