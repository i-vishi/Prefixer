package dev.vishalgaur.prefixerapp.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CitySearchModel(
    val name: String,
    val lat: Float,
    val long: Float,
) : Parcelable
