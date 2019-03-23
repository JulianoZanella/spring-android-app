package com.julianozanella.springandroidapp.service.webService

import com.julianozanella.springandroidapp.dto.ClienteDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientService {

    @GET("clientes/{id}")
    fun findById(@Path("id") id: String): Call<ClienteDTO>

    @GET("clientes/email")
    fun findByEmail(@Query("value") email: String): Call<ClienteDTO>
}