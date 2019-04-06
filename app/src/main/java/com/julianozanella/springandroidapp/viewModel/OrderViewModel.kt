package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.dto.PedidoDTO
import com.julianozanella.springandroidapp.repository.ClientRepository
import com.julianozanella.springandroidapp.repository.OrderRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val clientRepository = ClientRepository.getInstance()
    private val orderRepository = OrderRepository.getInstance()

    val pedidoDTO = MutableLiveData<PedidoDTO>()

    fun findById(id: String) = clientRepository.findById(id)

    fun findByEmail(email: String) = clientRepository.findByEmail(email)

    fun insert(pedidoDTO: PedidoDTO) = orderRepository.insert(pedidoDTO)
}