package dev.vishalgaur.prefixer.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log

internal class SharedPreferencesManager(context: Context, val preferenceFileName: String) {

    private var sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE)

    /**
     * Fetches all key-value pairs stored in the SharedPreferences.
     *
     * @return A map containing all the key-value pairs.
     */
    fun getAllPreferences(): Map<String, *> {
        return sharedPreferences.all
    }

    fun updateSharedPreference(pref: Pair<String, PrefValueType>) {
        try {
            val editor = sharedPreferences.edit()
            when (pref.second) {
                is PrefValueType.BooleanType -> {
                    editor.putBoolean(pref.first, pref.second.value as Boolean)
                }
                is PrefValueType.LongType -> {
                    editor.putLong(pref.first, pref.second.value as Long)
                }
                is PrefValueType.StringType -> {
                    editor.putString(pref.first, pref.second.value as String)
                }
                is PrefValueType.FloatType -> {
                    editor.putFloat(pref.first, pref.second.value as Float)
                }
                is PrefValueType.IntType -> {
                    editor.putInt(pref.first, pref.second.value as Int)
                }
            }
            editor.apply()
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString(), e)
        }
    }

    private fun getDefaultSharedPreferencesName(context: Context): String {
        return if (Build.VERSION.SDK_INT >= 24) {
            PreferenceManager.getDefaultSharedPreferencesName(context)
        } else
            context.packageName + "_preferences"
    }
//
//    private fun getSharedPreferencesFor(name: String): SharedPreferences {
//        for ((key, value) in sharedPreferences.entrySet()) {
//            if (value.name == name) {
//                return key
//            }
//        }
//        throw IllegalStateException("Unknown shared preferences $name")
//    }

    companion object {
        const val TAG = "SharedPrefsManager"
    }
}
