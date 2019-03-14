package com.julianozanella.springandroidapp.domain

import java.io.Serializable

data class Produto(val id: String = "", val nome: String = "", val preco: Double = 0.0) : Serializable {
    override fun toString(): String {
        return "Produto(id='$id', nome='$nome', preco=$preco)"
    }
}