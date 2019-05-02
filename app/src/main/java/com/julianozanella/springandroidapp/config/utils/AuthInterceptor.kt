package com.julianozanella.springandroidapp.config.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("Authorization", token)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}