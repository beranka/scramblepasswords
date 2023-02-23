package com.example.scramblepasswords

import android.app.Application
import android.os.Build
import com.google.android.material.color.DynamicColors

class ScramblePasswords: Application() {
    override fun onCreate() {
        super.onCreate()
        // Apply dynamic color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            DynamicColors.applyToActivitiesIfAvailable(this)
        }
    }
}