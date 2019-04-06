package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.service.webService.CategoriaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaRepository() {

    private val categoriaService: CategoriaService = RetrofitConfig().getCategoriaService()

    val categorias: LiveData<List<Categoria>>
        get() {
            val data = MutableLiveData<List<Categoria>>()
            categoriaService.getCategorias()
                .enqueue(object : Callback<List<Categoria>> {
                    override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                        data.value = null
                    }
                })
            return data
        }

    fun getProdutos(id: String): LiveData<Categoria> {
        val data = MutableLiveData<Categoria>()
        categoriaService.getProdutos(id)
            .enqueue(object : Callback<Categoria> {
                override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }
                override fun onFailure(call: Call<Categoria>, t: Throwable) {
                    data.value = null
                }
            })
        return data
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


