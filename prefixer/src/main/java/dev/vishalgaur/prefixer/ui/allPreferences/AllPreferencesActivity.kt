package dev.vishalgaur.prefixer.ui.allPreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.vishalgaur.prefixer.Prefixer
import dev.vishalgaur.prefixer.base.PrefValueType
import dev.vishalgaur.prefixer.base.SharedPreferencesManager
import dev.vishalgaur.prefixer.ui.models.PreferencesPair
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme

class AllPreferencesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val spManager: SharedPreferencesManager = Prefixer.INSTANCE.sharedPreferencesManager
        val allPrefs = spManager.getAllPreferences()
        setContent {
            PrefixerTheme {
                AllPreferencesScreen(
                    prefsName = spManager.preferenceFileName,
                    prefsList = allPrefs.getPrefsList(),
                    onUpdatePref = {
                        spManager.updateSharedPreference(it.key to it.value)
                    },
                )
            }
        }
    }

    private fun Map<String, *>.getPrefsList(): List<PreferencesPair> {
        val prefList = mutableListOf<PreferencesPair>()
        this.forEach { (t, u) ->
            val value: PrefValueType = when (u) {
                is Number -> PrefValueType.LongType(u.toLong())
                is Boolean -> PrefValueType.BooleanType(u)
                else -> PrefValueType.StringType(u?.toString())
            }
            prefList.add(
                PreferencesPair(t, value),
            )
        }
        return prefList
    }
}
