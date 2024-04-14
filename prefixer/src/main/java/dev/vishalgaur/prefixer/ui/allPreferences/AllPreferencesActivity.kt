package dev.vishalgaur.prefixer.ui.allPreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import dev.vishalgaur.prefixer.Prefixer
import dev.vishalgaur.prefixer.base.SharedPreferencesManager
import dev.vishalgaur.prefixer.domain.viewModel.AllPreferencesViewModel
import dev.vishalgaur.prefixer.ui.theme.PrefixerTheme

internal class AllPreferencesActivity : ComponentActivity() {

    private val viewModel: AllPreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val spManager: SharedPreferencesManager = Prefixer.INSTANCE.sharedPreferencesManager
        viewModel.getAllPreferences(spManager)

        setContent {
            PrefixerTheme {
                val allPrefs by viewModel.allPreferencesFlow.collectAsState()
                val isLoading by viewModel.isLoading.observeAsState()

                AllPreferencesScreen(
                    prefsName = spManager.preferenceFileName
                        ?: SharedPreferencesManager.getDefaultSharedPreferencesName(this),
                    prefsList = allPrefs,
                    isLoading = isLoading != false,
                    onUpdatePref = {
                        viewModel.updateSharedPreference(spManager, it)
                    },
                )
            }
        }
    }
}
