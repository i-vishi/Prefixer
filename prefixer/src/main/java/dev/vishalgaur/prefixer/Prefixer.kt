package dev.vishalgaur.prefixer

import android.content.Context
import android.content.Intent
import dev.vishalgaur.prefixer.core.SharedPreferencesManager
import dev.vishalgaur.prefixer.ui.allPreferences.AllPreferencesActivity

class Prefixer private constructor(context: Context, preferenceFileName: String) {

    internal var sharedPreferencesManager: SharedPreferencesManager
        private set

    init {
        sharedPreferencesManager = SharedPreferencesManager(context, preferenceFileName)
    }

    companion object {
        @Volatile
        internal lateinit var INSTANCE: Prefixer
            private set

        @Synchronized
        fun initialize(context: Context, preferenceFileName: String): Prefixer {
            return synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE = Prefixer(context, preferenceFileName)
                }
                INSTANCE
            }
        }
    }

    fun getLaunchIntent(context: Context): Intent {
        return Intent(context, AllPreferencesActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
