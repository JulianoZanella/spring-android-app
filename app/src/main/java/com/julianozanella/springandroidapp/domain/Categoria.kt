package com.julianozanella.springandroidapp.domain

import java.io.Serializable
import java.util.*

data class Categoria(val id: String = "", val nome: String = "", val produtos: Array<Produto>? = null) : Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Categoria

        if (id != other.id) return false
        if (nome != other.nome) return false
        if (!Arrays.equals(produtos, other.produtos)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nome.hashCode()
        result = 31 * result + Arrays.hashCode(produtos)
        return result
    }

    override fun toString(): String {
        return "Categoria(id='$id', nome='$nome', produtos=${Arrays.toString(produtos)})"
    }
}