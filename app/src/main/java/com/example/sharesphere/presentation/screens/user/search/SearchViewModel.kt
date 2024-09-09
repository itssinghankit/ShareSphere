package com.example.sharesphere.presentation.screens.user.search

import android.view.SearchEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sharesphere.R
import com.example.sharesphere.domain.model.user.search.SearchUserModel
import com.example.sharesphere.domain.use_case.user.common.follow.FollowUserUseCase
import com.example.sharesphere.domain.use_case.user.common.userId.GetUserIdDataStoreUseCase
import com.example.sharesphere.domain.use_case.user.profile.ViewAccountUseCase
import com.example.sharesphere.domain.use_case.user.search.SearchUserUseCase
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class SearchStates(
    val isLoading:Boolean=false,
    val errorMessage:UiText?=null,
    val searchResult:List<SearchUserModel>? = null,
    val userId:String?=null
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val searchUserUseCase: SearchUserUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val getUserIdDataStoreUseCase: GetUserIdDataStoreUseCase
):ViewModel() {
    private val _uiStates= MutableStateFlow(SearchStates())
    val uiStates:StateFlow<SearchStates> = _uiStates.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    val networkState=networkMonitor.networkState

    private var job:Job?=null

    fun onEvent(event: SearchEvents){
        when(event){
            is SearchEvents.ResetErrorMessage -> {
                _uiStates.update {
                    it.copy(errorMessage = null)
                }
            }
            is SearchEvents.OnSearchQueryChanged -> {
                searchQuery=event.searchQuery
                job?.cancel()
                if(searchQuery.isNotEmpty()){
                    job= viewModelScope.launch {
                        delay(500)
                        searchUser(searchQuery)
                    }
                }else{
                    onEvent(SearchEvents.OnSearchActiveClosed)
                    _uiStates.update {
                        it.copy(searchResult = null)
                    }
                }

            }

            is SearchEvents.OnFollowClicked -> {
                followUser(event.userId)
            }

            SearchEvents.OnSearchActiveClosed -> {
                job?.cancel()
                _uiStates.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }

    }

    private fun searchUser(usernameOrName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.update {
                it.copy(isLoading = true)
            }
            searchUserUseCase(usernameOrName).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _uiStates.update {
                                it.copy(
                                    isLoading = false,
                                    searchResult = result.data
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun followUser(accountId:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId=getUserIdDataStoreUseCase()
            if(userId==accountId){
                withContext(Dispatchers.Main){
                    _uiStates.update {
                        it.copy(
                            errorMessage = UiText.StringResource(R.string.errorFollowSelf)
                        )
                    }
                }
                return@launch
            }
            followUserUseCase(accountId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _uiStates.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        //do nothing
                    }
                }
            }
        }
    }
}