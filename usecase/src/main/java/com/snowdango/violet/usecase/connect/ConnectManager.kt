package com.snowdango.violet.usecase.connect

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build

class ConnectManager(private val context: Context) {

    fun isConnectInternet(): Boolean {
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
            return networkCapabilities != null
        } else {
            // below Marshmallow
            val activeNetwork = connManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable
        }
    }

}