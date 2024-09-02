package com.example.sharesphere.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.data.paging.HomePostRemoteMediator
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.data.remote.dto.user.home.like.LikePostDto
import com.example.sharesphere.data.remote.dto.user.home.save.SavePostDto
import com.example.sharesphere.domain.repository.UserRepositoryInterface
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.Constants.ITEMS_PER_PAGE
import com.example.sharesphere.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
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

    override suspend fun likePost(postId: String): Flow<ApiResult<LikePostDto, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.likePost(postId)
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun savePost(postId: String): Flow<ApiResult<SavePostDto, DataError.Network>> =
        flow {
            try {
                val response = shareSphereApi.savePost(postId)
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    500 -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

}