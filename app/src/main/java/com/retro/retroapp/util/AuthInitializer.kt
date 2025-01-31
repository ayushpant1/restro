package com.retro.retroapp.util

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.retro.retroapp.BuildConfig.AUTH_EMULATOR_HOST
import com.retro.retroapp.BuildConfig.AUTH_EMULATOR_PORT

class AuthInitializer : Initializer<FirebaseAuth> {
    override fun create(context: Context): FirebaseAuth {
        val firebaseAuth = Firebase.auth
        // Use emulators only in debug builds
        if (DebugHelper.isDebug) {
            firebaseAuth.useEmulator(AUTH_EMULATOR_HOST, AUTH_EMULATOR_PORT)
        }
        return firebaseAuth
    }

    // No dependencies on other libraries
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}