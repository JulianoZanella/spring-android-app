package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import com.julianozanella.springandroidapp.repository.ClientRepository

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val clientRepository = ClientRepository.getInstance(application)

    fun insert(clienteNewDTO: ClienteNewDTO) = clientRepository.insert(clienteNewDTO)
}