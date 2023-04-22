package com.example.foodrecipeapp.interactors.app

import android.util.Log
import com.example.foodrecipeapp.util.TAG
import java.net.Socket
import javax.net.SocketFactory

object DoesNetworkHaveInternet {
    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            val socket = socketFactory.createSocket()
            socket.connect(java.net.InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "Ping success")
            true
        }
        catch (e: Exception) {
            Log.d(TAG, "No Internet Connection: ${e.message}")
            false
        }
    }
}