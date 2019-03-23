package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.repository.ClientRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val clientRepository = ClientRepository.getInstance(application)
    val selectedClient = MutableLiveData<ClienteDTO>()

    fun findById(id: String) = clientRepository.findById(id).also { selectedClient.value = it.value }

    fun findByEmail(email: String) = clientRepository.findByEmail(email).also { selectedClient.value = it.value }
}