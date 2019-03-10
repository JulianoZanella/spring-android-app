package com.julianozanella.springandroidapp.service

import com.julianozanella.springandroidapp.domain.Categoria
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriaService {

    @GET("categorias")
    fun getCategorias(): Call<List<Categoria>>
}