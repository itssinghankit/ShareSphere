package com.example.sharesphere.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sharesphere.data.local.HomePostDatabase
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.commonDto.user.home.post.PostRemoteKeysDto
import com.example.sharesphere.data.remote.ShareSphereApi
import com.example.sharesphere.util.Constants.ITEMS_PER_PAGE
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HomePostRemoteMediator @Inject constructor(
    private val shareSphereApi: ShareSphereApi,
    private val homePostDatabase: HomePostDatabase
) : RemoteMediator<Int, Post>(){

    private val postDao= homePostDatabase.postDao()
    private val postRemoteKeysDao= homePostDatabase.postRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
       return try {
           val currentPage=when(loadType){
               LoadType.REFRESH -> {
                     val remoteKeys = getRemoteKeyClosestToCurrentPage(state)
                   remoteKeys?.nextPage?.minus(1)?:1
               }
               LoadType.PREPEND -> {
                   val remoteKeys= getRemoteKeyForFirstItem(state)
                   val prevPage = remoteKeys?.prevPage?: return MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)
                   prevPage
               }
               LoadType.APPEND -> {
                   val remoteKeys = getRemoteKeyForLastItem(state)
                   val nextPage = remoteKeys?.nextPage?: return MediatorResult.Success(endOfPaginationReached = remoteKeys!=null)
                   nextPage
               }
           }
           val response = shareSphereApi.getAllPosts(page = currentPage, limit = ITEMS_PER_PAGE)
           val endOfPaginationReached = response.data.posts.isEmpty()

           val prevPage = if(currentPage==1) null else currentPage-1
           val nextPage= if(endOfPaginationReached) null else currentPage+1

            homePostDatabase.withTransaction {
                if(loadType == LoadType.REFRESH){
                    postDao.deleteAllPosts()
                    postRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys= response.data.posts.map{post->
                    PostRemoteKeysDto(
                        id = post._id,
                        prevPage = prevPage,
                        nextPage=nextPage
                    )

                }
                postRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                postDao.addPosts(posts = response.data.posts)

            }
           MediatorResult.Success(endOfPaginationReached=endOfPaginationReached)


       }catch (e:Exception){
           Timber.e(e)
            return MediatorResult.Error(e)
       }
    }

    private suspend fun getRemoteKeyClosestToCurrentPage(
        state: PagingState<Int, Post>
    ): PostRemoteKeysDto?{
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?._id?.let { id->
                postRemoteKeysDao.getRemoteKeys(postId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Post>
    ): PostRemoteKeysDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?._id?.let { id ->
            postRemoteKeysDao.getRemoteKeys(postId = id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Post>
    ): PostRemoteKeysDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?._id?.let { id ->
            postRemoteKeysDao.getRemoteKeys(postId = id)
        }
    }

}