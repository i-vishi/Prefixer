package dev.vishalgaur.prefixer.ui.models

import androidx.annotation.Keep

@Keep
data class PreferencesPair(
    val key: String,
    val value: Any?,
)
