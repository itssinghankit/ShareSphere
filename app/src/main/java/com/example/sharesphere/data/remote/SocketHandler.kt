package com.example.sharesphere.data.remote

import com.example.sharesphere.BuildConfig
import com.example.sharesphere.data.repository.datastore.PreferencesKeys
import com.example.sharesphere.domain.repository.DataStoreRepository
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.net.URISyntaxException
import javax.inject.Inject

class SocketHandler @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            val token = runBlocking {
                dataStoreRepository.getString(PreferencesKeys.ACCESS_TOKEN).first()
            }
            val options = IO.Options().apply {
                auth = mapOf("token" to token)
                timeout = 60000
            }
            mSocket = IO.socket(BuildConfig.SOCKET_BASE_URL,options)

        } catch (e: URISyntaxException) {
            Timber.d(e)
        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}