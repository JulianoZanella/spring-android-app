package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class ClienteDTO(
    var id: String = "",
    var nome: String = "",
    var email: String = "",
    var enderecos: Array<EnderecoDTO>? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClienteDTO

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}




