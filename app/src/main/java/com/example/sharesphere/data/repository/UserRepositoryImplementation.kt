package com.example.sharesphere.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.data.paging.HomePostRemoteMediator
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import com.example.sharesphere.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImplementation @Inject constructor(
    private val shareSphereApi: ShareSphereApi,
    private val homePostDatabase: HomePostDatabase
) : UserRepositoryInterface {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPosts(): Flow<PagingData<Post>> {
        val pagingSourceFactory = { homePostDatabase.postDao().getAllPosts() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HomePostRemoteMediator(
                shareSphereApi = shareSphereApi,
                homePostDatabase = homePostDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}