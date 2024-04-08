package dev.vishalgaur.prefixerapp.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CitySearchData(
    val name: String,
    val latitude: Float,
    val longitude: Float,
) : Parcelable
