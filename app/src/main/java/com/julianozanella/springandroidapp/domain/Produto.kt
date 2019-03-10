package com.julianozanella.springandroidapp.domain

data class Produto(val id: String = "", val nome: String = "", val preco: Double = 0.0) {
    override fun toString(): String {
        return "Produto(id='$id', nome='$nome', preco=$preco)"
    }
}