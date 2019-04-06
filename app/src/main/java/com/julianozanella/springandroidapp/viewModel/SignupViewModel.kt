package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.julianozanella.springandroidapp.dto.CidadeDTO
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import com.julianozanella.springandroidapp.repository.ClientRepository
import com.julianozanella.springandroidapp.repository.EstadoRepository

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val clientRepository = ClientRepository.getInstance()

    private val estadoRepository = EstadoRepository.getInstance()

    private val filtro = MutableLiveData<FiltroPorId>()

    fun insert(clienteNewDTO: ClienteNewDTO) = clientRepository.insert(clienteNewDTO)

    fun getEstados() = estadoRepository.estados

    fun getCidades(): LiveData<List<CidadeDTO>> {
        return Transformations.switchMap(filtro) { estadoRepository.getCidades(it.id) }
    }

    fun setEstado(id: String) {
        filtro.value = FiltroPorId(id)
    }

    inner class FiltroPorId(var id: String)
}