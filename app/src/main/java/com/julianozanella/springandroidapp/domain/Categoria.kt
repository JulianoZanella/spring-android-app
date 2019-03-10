package com.julianozanella.springandroidapp.domain

import java.io.Serializable
import java.util.*

data class Categoria(val id: String = "", val nome: String = "") : Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Categoria

        if (id != other.id) return false
        if (nome != other.nome) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nome.hashCode()
        return result
    }

    override fun toString(): String {
        return "Categoria(id='$id', nome='$nome'})"
    }
}