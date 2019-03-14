package com.julianozanella.springandroidapp.config

import com.julianozanella.springandroidapp.service.CategoriaService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitConfig {
    private val baseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    fun getCategoriaService() = this.baseRetrofit.create(CategoriaService::class.java)

}