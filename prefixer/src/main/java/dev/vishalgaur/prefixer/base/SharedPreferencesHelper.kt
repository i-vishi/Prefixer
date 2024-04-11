package dev.vishalgaur.prefixer.base

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import java.io.File

class SharedPreferencesHelper {

    companion object {
        private const val SHARED_PREFS_DIR = "shared_prefs"
        private const val XML_SUFFIX = ".xml"
    }

    private val mSharedPreferences: Map<SharedPreferences, SharedPreferencesDescriptor>? = null
//    private val onSharedPreferenceChangeListener =
//        OnSharedPreferenceChangeListener { sharedPreferences, key ->
//            if (mConnection == null) {
//                return@OnSharedPreferenceChangeListener
//            }
//            val descriptor =
//                mSharedPreferences!![sharedPreferences] ?: return@OnSharedPreferenceChangeListener
//            mConnection.send(
//                "sharedPreferencesChange",
//                Builder()
//                    .put("preferences", descriptor.name)
//                    .put("name", key)
//                    .put("deleted", !sharedPreferences.contains(key))
//                    .put("time", System.currentTimeMillis())
//                    .put("value", sharedPreferences.all[key])
//                    .build()
//            )
//        }

    private fun buildDescriptorForAllPrefsFiles(
        context: Context,
    ): List<SharedPreferencesDescriptor> {
        val dir = File(context.applicationInfo.dataDir, SHARED_PREFS_DIR)
        val list = dir.list { dir, name -> name.endsWith(XML_SUFFIX) }
        val descriptors: MutableList<SharedPreferencesDescriptor> = ArrayList()
        if (list != null) {
            for (each in list) {
                val prefName = each.substring(0, each.indexOf(XML_SUFFIX))
                descriptors.add(SharedPreferencesDescriptor(prefName, MODE_PRIVATE))
            }
        }

        descriptors.add(
            SharedPreferencesDescriptor(getDefaultSharedPreferencesName(context), MODE_PRIVATE),
        )

        return descriptors
    }

    private fun getDefaultSharedPreferencesName(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
        ) {
            PreferenceManager.getDefaultSharedPreferencesName(context)
        } else context.packageName + "_preferences"
    }

    private fun getSharedPreferencesFor(name: String): SharedPreferences {
        for ((key, value) in mSharedPreferences!!) {
            if (value.name == name) {
                return key
            }
        }
        throw IllegalStateException("Unknown shared preferences $name")
    }

    class SharedPreferencesDescriptor(val name: String, val mode: Int) {
        fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(name, mode)
        }
    }
}
