package dev.vishalgaur.prefixer.base

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context, val preferenceFileName: String) {

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

//    private fun getDefaultSharedPreferencesName(context: Context): String {
//        return PreferenceManager.getDefaultSharedPreferencesName(context)
//    }
//
//    private fun getSharedPreferencesFor(name: String): SharedPreferences {
//        for ((key, value) in sharedPreferences.entrySet()) {
//            if (value.name == name) {
//                return key
//            }
//        }
//        throw IllegalStateException("Unknown shared preferences $name")
//    }
}
