package dev.vishalgaur.prefixerapp.repository

import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.models.CitySearchData

interface CitySearchRepo {

    suspend fun searchACity(query: String): Result<List<CitySearchData>>
}
