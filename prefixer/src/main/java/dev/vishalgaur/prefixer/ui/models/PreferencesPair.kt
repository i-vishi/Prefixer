package dev.vishalgaur.prefixer.ui.models

import androidx.annotation.Keep
import dev.vishalgaur.prefixer.base.PrefValueType

@Keep
internal data class PreferencesPair(
    val key: String,
    val value: PrefValueType,
)
