package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.service.webService.CategoriaService

class CategoriaRepository : Repository() {

    private val categoriaService: CategoriaService = RetrofitConfig().getCategoriaService()

    val categorias: LiveData<List<Categoria>>
        get() {
            return getLiveData(categoriaService.getCategorias())
        }

    fun getProdutos(id: String): LiveData<Categoria> {
        return getLiveData(categoriaService.getProdutos(id))
    }

    companion object {

        private var INSTANCE: CategoriaRepository? = null

        fun getInstance(): CategoriaRepository {
            if (INSTANCE == null) {
                INSTANCE = CategoriaRepository()
            }
            return INSTANCE as CategoriaRepository
        }
    }
}


