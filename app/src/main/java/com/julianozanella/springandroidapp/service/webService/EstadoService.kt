package com.julianozanella.springandroidapp.service.webService

import com.julianozanella.springandroidapp.dto.CidadeDTO
import com.julianozanella.springandroidapp.dto.EstadoDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EstadoService {

    @GET("estados")
    fun getEstados(): Call<List<EstadoDTO>>

    @GET("estados/{id}/cidades")
    fun getCidades(@Path("id") id: String): Call<List<CidadeDTO>>
}