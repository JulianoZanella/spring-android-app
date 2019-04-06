package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.CidadeDTO
import com.julianozanella.springandroidapp.dto.EstadoDTO
import com.julianozanella.springandroidapp.service.webService.EstadoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstadoRepository {

    private val estadoService: EstadoService = RetrofitConfig().getStatesService()

    val estados: LiveData<List<EstadoDTO>>
        get() {
            val data = MutableLiveData<List<EstadoDTO>>()
            estadoService.getEstados()
                .enqueue(object : Callback<List<EstadoDTO>> {
                    override fun onResponse(call: Call<List<EstadoDTO>>, response: Response<List<EstadoDTO>>) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<List<EstadoDTO>>, t: Throwable) {
                        data.value = null
                    }
                })
            return data
        }

    fun getCidades(id: String): LiveData<List<CidadeDTO>> {
        val data = MutableLiveData<List<CidadeDTO>>()
        estadoService.getCidades(id)
            .enqueue(object : Callback<List<CidadeDTO>> {
                override fun onResponse(call: Call<List<CidadeDTO>>, response: Response<List<CidadeDTO>>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<CidadeDTO>>, t: Throwable) {
                    data.value = null
                }
            })
        return data
    }

    companion object {

        private var INSTANCE: EstadoRepository? = null

        fun getInstance(): EstadoRepository {
            if (INSTANCE == null) {
                INSTANCE = EstadoRepository()
            }
            return INSTANCE as EstadoRepository
        }
    }
}


