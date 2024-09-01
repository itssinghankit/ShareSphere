package com.example.sharesphere.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sharesphere.data.local.dao.HomePostDao
import com.example.sharesphere.data.local.dao.HomePostRemoteKeysDao
import com.example.sharesphere.data.commonDto.user.home.post.Post
import com.example.sharesphere.data.commonDto.user.home.post.PostRemoteKeysDto

@Database(entities = [Post::class, PostRemoteKeysDto::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class HomePostDatabase:RoomDatabase() {
    abstract fun postDao(): HomePostDao
    abstract fun postRemoteKeysDao(): HomePostRemoteKeysDao
}