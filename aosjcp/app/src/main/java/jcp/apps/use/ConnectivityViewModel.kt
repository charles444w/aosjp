package jcp.apps.use

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ConnectivityViewModel(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver.isConnected
        .stateIn(
            scope = viewModelScope, // Lifecycle-aware scope
            started = SharingStarted.WhileSubscribed(5000), // Share while subscribed
            initialValue = false // Start with 'disconnected'
        )
}