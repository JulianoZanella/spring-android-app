package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class CidadeDTO(
    var id: String = "",
    var nome: String = "",
    var estado: EstadoDTO? = null
) : Serializable