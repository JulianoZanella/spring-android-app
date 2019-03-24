package com.julianozanella.springandroidapp.service.webService

import com.julianozanella.springandroidapp.dto.PedidoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {

    @POST("pedidos")
    fun insert(@Body pedido: PedidoDTO): Call<PedidoDTO>
}