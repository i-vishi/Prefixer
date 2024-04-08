package dev.vishalgaur.prefixerapp.repository

import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.models.CitySearchData
import dev.vishalgaur.prefixerapp.network.RetrofitInstance
import dev.vishalgaur.prefixerapp.network.api.CitySearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class CitySearchRepoImpl : CitySearchRepo {

    private val service: CitySearchService by lazy { RetrofitInstance.getInstance(false).create(CitySearchService::class.java) }

    override suspend fun searchACity(query: String): Result<List<CitySearchData>> {
        return withContext(Dispatchers.IO) {
            val deferredRes = async {
                service.searchCity(query)
            }
            val response = deferredRes.await()
            if (response != null) {
                Result.Success(response)
            } else {
                Result.Error(Exception("Some Error Occurred"))
            }
        }
    }
}
