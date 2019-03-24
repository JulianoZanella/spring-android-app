package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.PedidoDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(context: Context) {

    private val service = RetrofitConfig(context).getOrderService()

    fun insert(pedidoDTO: PedidoDTO): LiveData<String> {
        val data = MutableLiveData<String>()
        service.insert(pedidoDTO).enqueue(object : Callback<PedidoDTO> {
            override fun onFailure(call: Call<PedidoDTO>, t: Throwable) {
                Log.d("Error", "Erro retrofit:\n${t.message}")
                data.value = null
            }

            override fun onResponse(call: Call<PedidoDTO>, response: Response<PedidoDTO>) {
                val location = response.headers().get("location")
                data.value = extractId(location)
            }
        })
        return data
    }

    private fun extractId(location: String?): String? {
        location ?: return null
        val position = location.lastIndexOf("/")
        return location.substring(position + 1, location.length)
    }


    companion object {
        private var INSTANCE: OrderRepository? = null

        fun getInstance(context: Context): OrderRepository {
            if (INSTANCE == null) {
                INSTANCE = OrderRepository(context)
            }
            return INSTANCE!!
        }
    }
}