package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.PedidoDTO

class OrderRepository : Repository() {

    private val service = RetrofitConfig().getOrderService()

    fun insert(pedidoDTO: PedidoDTO): LiveData<String> = getLiveDataFromHeader(service.insert(pedidoDTO), HEADER.ID)

    companion object {
        private var INSTANCE: OrderRepository? = null

        fun getInstance(): OrderRepository {
            if (INSTANCE == null) {
                INSTANCE = OrderRepository()
            }
            return INSTANCE!!
        }
    }
}