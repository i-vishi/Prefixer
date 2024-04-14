package dev.vishalgaur.prefixer.domain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vishalgaur.prefixer.base.PrefValueType
import dev.vishalgaur.prefixer.base.SharedPreferencesManager
import dev.vishalgaur.prefixer.ui.models.PreferencesPair
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.SortedSet

internal class AllPreferencesViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _allPreferencesList = MutableStateFlow<List<PreferencesPair>>(emptyList())
    var allPreferencesFlow: StateFlow<List<PreferencesPair>> = _allPreferencesList.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _allPreferencesList.value,
    )

    fun getAllPreferences(spManager: SharedPreferencesManager) {
        viewModelScope.launch {
            _isLoading.value = true
            _allPreferencesList.update {
                val prefs = spManager.getAllPreferences()
                val prefsList = prefs.getPrefsList()
                delay(100)
                _isLoading.value = false
                prefsList
            }
        }
    }

    private fun updateAllPreferences(spManager: SharedPreferencesManager) {
        getAllPreferences(spManager)
    }

    fun updateSharedPreference(
        spManager: SharedPreferencesManager,
        preferencesPair: PreferencesPair,
    ) {
        viewModelScope.launch {
            spManager.updateSharedPreference(preferencesPair.key to preferencesPair.value)
            delay(100)
            updateAllPreferences(spManager)
        }
    }

    private fun Map<String, *>.getPrefsList(): List<PreferencesPair> {
        val prefList: SortedSet<PreferencesPair> =
            sortedSetOf(compareBy<PreferencesPair> { it.key })
        this.forEach { (t, u) ->
            val value: PrefValueType = when (u) {
                is Boolean -> PrefValueType.BooleanType(u)
                is Long -> PrefValueType.LongType(u)
                is Int -> PrefValueType.IntType(u)
                is Float -> PrefValueType.FloatType(u)
                else -> {
                    if (u == null || u is String) {
                        PrefValueType.StringType(u?.toString())
                    } else {
                        Log.e(TAG, "Invalid value type for key: $t, value: $u")
                        PrefValueType.StringType(u.toString())
                    }
                }
            }
            prefList.add(PreferencesPair(t, value))
        }
        return prefList.toList()
    }

    companion object {
        private const val TAG = "AllPreferencesViewModel"
    }
}
