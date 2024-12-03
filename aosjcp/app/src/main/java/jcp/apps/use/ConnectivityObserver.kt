package jcp.apps.use

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}