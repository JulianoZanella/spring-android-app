package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class PedidoDTO(
    val cliente: RefDTO,
    var enderecoDeEntrega: RefDTO?,
    var pagamento: PagamentoDTO?,
    val itens: Array<ItemPedidoDTO>
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PedidoDTO

        if (cliente != other.cliente) return false

        return true
    }

    override fun hashCode(): Int {
        return cliente.hashCode()
    }
}