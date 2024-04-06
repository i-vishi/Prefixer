package dev.vishalgaur.prefixerapp.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()

        Log.i(
            TAG,
            "Sending request ${request.url} on ${chain.connection()} \n${request.headers}",
        )

        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()

        Log.i(
            TAG,
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url,
                (t2 - t1) / 1e6,
                response.body?.byteStream(),
            ),
        )

        return response
    }

    companion object {
        private const val TAG = "LoggingInterceptor"
    }
}
