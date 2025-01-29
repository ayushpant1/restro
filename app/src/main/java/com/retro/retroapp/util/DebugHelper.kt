package com.retro.retroapp.util

import com.retro.retroapp.BuildConfig

object DebugHelper {
    val isDebug: Boolean
        get() = BuildConfig.DEBUG
}