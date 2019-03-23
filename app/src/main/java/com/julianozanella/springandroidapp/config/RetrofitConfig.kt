package com.julianozanella.springandroidapp.config

import android.content.Context
import com.julianozanella.springandroidapp.config.utils.NullOnEmptyConverterFactory
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.service.webService.AuthService
import com.julianozanella.springandroidapp.service.CategoriaService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitConfig(context: Context) {
    private val baseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(JacksonConverterFactory.create())
        .client(getRequestInterceptor(context))
        .build()

    private fun getRequestInterceptor(context: Context): OkHttpClient {
        val client = OkHttpClient.Builder()
        val token = context.getSharedPreference(KEY.TOKEN, String::class.java) as String?
        val tok = token ?: ""
        client.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Authorization", tok)
                val request: Request = requestBuilder.build()
                val response: Response = chain.proceed(request)
                /* TODO("Fazer error handler")
                if(response.code() > 300){
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
                */
                return response
            }
        })
        return client.build()
    }

    fun getCategoriaService() = this.baseRetrofit.create(CategoriaService::class.java)

    fun getAuthService() = this.baseRetrofit.create(AuthService::class.java)

}