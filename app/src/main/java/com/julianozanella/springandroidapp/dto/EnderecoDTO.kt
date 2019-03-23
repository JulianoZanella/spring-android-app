package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class EnderecoDTO
    (
    var id: String? = null,
    var logradouro: String? = null,
    var numero: String? = null,
    var complemento: String? = null,
    var bairro: String? = null,
    var cep: String? = null,
    var cidade: CidadeDTO? = null
) : Serializable
