package com.exercises.week3.model

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val finalRequest = request.newBuilder().url(url).build()
        return chain.proceed(finalRequest)
    }

}