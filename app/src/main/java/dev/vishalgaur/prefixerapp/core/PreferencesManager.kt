package dev.vishalgaur.prefixerapp.core

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveCurrentLocation(locationName: String) {
        sharedPreferences.edit().putString(KEY_LOCATION, locationName).apply()
    }

    fun getCurrentLocation(): String {
        return sharedPreferences.getString(KEY_LOCATION, "") ?: ""
    }

    companion object {
        private const val PREFS_NAME = "PrefixerAppPreferences"

        @Volatile
        private var instance: PreferencesManager? = null

        // ALL PREFERENCES KEYS
        private const val KEY_LOCATION = "current_location"

        fun getInstance(context: Context): PreferencesManager =
            instance ?: synchronized(this) {
                instance ?: PreferencesManager(context.applicationContext).also { instance = it }
            }
    }
}
