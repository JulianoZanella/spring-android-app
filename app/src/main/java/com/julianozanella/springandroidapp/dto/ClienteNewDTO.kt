package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class ClienteNewDTO(
    var nome: String = "",
    var email: String = "",
    var cpfOuCnpj: String = "",
    var tipo: Int = 1,
    var senha: String = "",
    var logradouro: String = "",
    var numero: String = "",
    var complemento: String? = null,
    var bairro: String = "",
    var cep: String = "",
    var telefone1: String = "",
    var telefone2: String? = null,
    var telefone3: String? = null,
    var cidadeId: String = ""
) : Serializable
