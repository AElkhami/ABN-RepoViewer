package com.elkhami.repoviewer.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkMonitor(private val context: Context, private val onNetworkStatusChanged: (Boolean) -> Unit) {
    private val connectivityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }

            val isConnected =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            onNetworkStatusChanged(isConnected)
        }
    }

    @Suppress("DEPRECATION")
    fun register() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(connectivityReceiver, filter)
    }

    fun unregister() {
        context.unregisterReceiver(connectivityReceiver)
    }
}
