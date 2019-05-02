package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.CidadeDTO
import com.julianozanella.springandroidapp.dto.EstadoDTO
import com.julianozanella.springandroidapp.service.webService.EstadoService

class EstadoRepository : Repository() {

    private val estadoService: EstadoService = RetrofitConfig().getStatesService()

    val estados: LiveData<List<EstadoDTO>>
        get() {
            return getLiveData(estadoService.getEstados())
        }

    fun getCidades(id: String): LiveData<List<CidadeDTO>> = getLiveData(estadoService.getCidades(id))

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


