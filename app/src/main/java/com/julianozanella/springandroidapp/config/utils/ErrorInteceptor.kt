package com.julianozanella.springandroidapp.config.utils

import com.google.gson.Gson
import com.julianozanella.springandroidapp.domain.ErrorMessage
import com.julianozanella.springandroidapp.view.BaseActivity
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import java.net.SocketTimeoutException

class ErrorInteceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            handleError(response)
        }
        return response
    }

    private fun handleError(response: Response) {
        val body = response.body()
        if (body != null) {
            val gson = Gson()
            val errorMessage: ErrorMessage = gson.fromJson(body.charStream(), ErrorMessage::class.java)
            BaseActivity.handleError(errorMessage)
        }
    }

    fun <T> handleError(call: Call<T>, throwable: Throwable) {
        val url: String = call.request().url().toString()
        val err =
            when (throwable) {
                is SocketTimeoutException -> {
                    ErrorMessage(
                        url,
                        "",
                        "",
                        System.currentTimeMillis().toString(),
                        -141
                    )
                }
                else -> {
                    ErrorMessage(
                        url,
                        throwable.localizedMessage ?: "",
                        throwable.message ?: "",
                        System.currentTimeMillis().toString(),
                        -144
                    )
                }
            }
        BaseActivity.handleError(err)
    }
}