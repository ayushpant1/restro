package com.retro.retroapp.ui.utils.networkconnection.networkconnection

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}