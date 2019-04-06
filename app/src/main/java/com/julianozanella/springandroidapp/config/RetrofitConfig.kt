package com.julianozanella.springandroidapp.config

import com.google.gson.Gson
import com.julianozanella.springandroidapp.config.utils.NullOnEmptyConverterFactory
import com.julianozanella.springandroidapp.domain.ErrorMessage
import com.julianozanella.springandroidapp.service.webService.*
import com.julianozanella.springandroidapp.view.BaseActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitConfig {

    private var baseRetrofit: Retrofit? = null

    private fun getRequestInterceptor(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val token = BaseActivity.getToken()
        val tok = token ?: ""
        client.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Authorization", tok)
                val request: Request = requestBuilder.build()
                val response: Response = chain.proceed(request)
                if (!response.isSuccessful) {//TODO("handle connection error")
                    handleError(response)
                }
                return response
            }
        })
        return client.build()
    }

    private fun handleError(response: Response) {
        val body = response.body()
        if (body != null) {
            val gson = Gson()
            val errorMessage: ErrorMessage = gson.fromJson(body.charStream(), ErrorMessage::class.java)
            BaseActivity.handleError(errorMessage)
        }
    }

    fun recreateRetrofit() {
        baseRetrofit?.newBuilder()?.client(getRequestInterceptor())?.build()
        baseRetrofit = null
    }

    private fun getRetrofit(): Retrofit {
        if (baseRetrofit == null) {
            baseRetrofit = Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getRequestInterceptor())
                .build()
        }
        return baseRetrofit!!
    }

    fun getCategoriaService() = this.getRetrofit().create(CategoriaService::class.java)

    fun getAuthService() = this.getRetrofit().create(AuthService::class.java)

    fun getClientService() = this.getRetrofit().create(ClientService::class.java)

    fun getOrderService() = this.getRetrofit().create(OrderService::class.java)

    fun getStatesService() = this.getRetrofit().create(EstadoService::class.java)

    fun getProfileService() = this.getRetrofit().create(ProfileService::class.java)
}