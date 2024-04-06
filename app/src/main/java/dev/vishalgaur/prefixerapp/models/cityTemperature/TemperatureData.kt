package com.example.simpleweatherapp.network.models.cityTemperature

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TemperatureData(
    val temp: Double,
    @SerializedName("temp_min")
    val minTemp: Double,
    @SerializedName("temp_max")
    val maxTemp: Double,
) : Parcelable
