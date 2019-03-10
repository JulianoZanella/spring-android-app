package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.domain.Categoria
import com.julianozanella.springandroidapp.repository.CategoriaRepository

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    val categorias: LiveData<List<Categoria>>
    private val categoriaRepository: CategoriaRepository = CategoriaRepository.getInstance()

    init {
        categorias = categoriaRepository.categorias
    }
}
