package com.foss.foss.util

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable

inline fun <reified T : Parcelable> Intent.parcelableArrayListCompat(key: String): ArrayList<T>? =
    when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> {
            @Suppress("DEPRECATION")
            getParcelableArrayListExtra(key)
        }
    }
