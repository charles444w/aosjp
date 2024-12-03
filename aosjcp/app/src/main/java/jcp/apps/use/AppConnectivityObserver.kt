package jcp.apps.use

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

//https://medium.com/@graser1305/how-to-observe-real-internet-connectivity-in-android-fb6ebd2e3e00
class AppConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isConnected: Flow<Boolean> = callbackFlow {
        // Network Callback for real-time updates
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true) // Emit true when internet is available
            }

            override fun onLost(network: Network) {
                trySend(false) // Emit false when connection is lost
            }

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                // Validate actual internet access
                trySend(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            }
        }

        // Register the callback
        connectivityManager.registerDefaultNetworkCallback(callback)

        // Cleanup when the flow is cancelled
        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
}