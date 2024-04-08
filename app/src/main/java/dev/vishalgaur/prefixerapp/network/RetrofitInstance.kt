package dev.vishalgaur.prefixerapp.network

import dev.vishalgaur.prefixerapp.network.base.ApiConstants
import dev.vishalgaur.prefixerapp.network.interceptors.LoggingInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    @Volatile
    private lateinit var INSTANCE: Retrofit

    private fun createInstance(
        baseUrl: String = ApiConstants.WEATHER_BASE_URL,
        authKey: String = ApiConstants.WEATHER_APP_ID,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(),
            )
            .client(okHttpClient(authKey))
            .build()
    }

    private fun createInstanceForCityApi(
        baseUrl: String = ApiConstants.CITY_API_BASE_URL,
        authKey: String = ApiConstants.CITY_API_KEY,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(),
            )
            .client(okHttpClientForCityApi(authKey))
            .build()
    }

    fun getInstance(forWeather: Boolean = true): Retrofit {
        return synchronized(this) {
            if (!RetrofitInstance::INSTANCE.isInitialized) {
                INSTANCE = if (forWeather) createInstance() else createInstanceForCityApi()
            }
            INSTANCE
        }
    }

    private fun okHttpClient(authKey: String): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val request = chain.request()
                val url = request.url.newBuilder().addQueryParameter(ApiConstants.QUERY_APP_ID, authKey).build()
                val builder = request.newBuilder().url(url)
                return@Interceptor chain.proceed(builder.build())
            },
        ).addInterceptor(LoggingInterceptor())
    }.build()

    private fun okHttpClientForCityApi(authKey: String): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val request = chain.request()
                val headers = request.headers.newBuilder().add("", authKey).build()
                val builder = request.newBuilder().headers(headers)
                return@Interceptor chain.proceed(builder.build())
            },
        ).addInterceptor(LoggingInterceptor())
    }.build()
}
