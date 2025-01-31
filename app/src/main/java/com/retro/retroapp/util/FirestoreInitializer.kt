package com.retro.retroapp.util

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.retro.retroapp.BuildConfig.FIRESTORE_EMULATOR_HOST
import com.retro.retroapp.BuildConfig.FIRESTORE_EMULATOR_PORT

class FirestoreInitializer : Initializer<FirebaseFirestore> {
    override fun create(context: Context): FirebaseFirestore {
        val firestore = Firebase.firestore
        // Use emulators only in debug builds
        if (DebugHelper.isDebug) {
            firestore.useEmulator(FIRESTORE_EMULATOR_HOST, FIRESTORE_EMULATOR_PORT)
        }
        return firestore
    }

    // No dependencies on other libraries
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}