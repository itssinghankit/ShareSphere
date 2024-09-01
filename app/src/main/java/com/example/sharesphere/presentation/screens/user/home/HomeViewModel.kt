package com.example.sharesphere.presentation.screens.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.domain.use_case.user.home.GetAllPostUseCase
import com.example.sharesphere.util.NetworkMonitor
import com.example.sharesphere.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val posts: Flow<PagingData<Post>> = emptyFlow()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getAllPostUseCase: GetAllPostUseCase
) : ViewModel() {

//    init {
//        getAllPosts()
//    }

    private val _uiState = MutableStateFlow(HomeStates())
    val uiState: StateFlow<HomeStates> = _uiState

    val posts = getAllPostUseCase()
    val networkState = networkMonitor.networkState

    fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.ResetErrorMessage -> {
                _uiState.update {
                    it.copy(errorMessage = null)
                }
            }
        }

    }

//    private fun getAllPosts() {
//        viewModelScope.launch {
//            _uiState.update {
//                it.copy(posts = getAllPostUseCase())
//            }
//        }
//    }

}