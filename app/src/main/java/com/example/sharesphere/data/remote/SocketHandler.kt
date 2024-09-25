package com.example.sharesphere.data.remote

import com.example.sharesphere.BuildConfig
import io.socket.client.IO
import io.socket.client.Socket
import timber.log.Timber
import java.net.URISyntaxException

object SocketHandler {

    private lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(BuildConfig.SOCKET_BASE_URL)
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