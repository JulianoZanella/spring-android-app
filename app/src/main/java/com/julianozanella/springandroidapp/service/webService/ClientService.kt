package com.julianozanella.springandroidapp.service.webService

import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import retrofit2.Call
import retrofit2.http.*

interface ClientService {

    @GET("clientes/{id}")
    fun findById(@Path("id") id: String): Call<ClienteDTO>

    @GET("clientes/email")
    fun findByEmail(@Query("value") email: String): Call<ClienteDTO>

    @POST("clientes")
    fun insert(@Body clienteNewDTO: ClienteNewDTO): Call<ClienteNewDTO>
}