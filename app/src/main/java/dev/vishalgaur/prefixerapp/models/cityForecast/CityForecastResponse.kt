package dev.vishalgaur.prefixerapp.models.cityForecast

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CityForecastResponse(
    @SerializedName("cod")
    val responseCode: Int,
    @SerializedName("cnt")
    val listItemsCount: Int?,
    val list: List<ForecastItem>?,
) : Parcelable
