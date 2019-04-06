package com.julianozanella.springandroidapp.domain

import java.io.Serializable

data class ErrorMessage(
    val path: String = "",
    val error: String = "",
    val message: String = "",
    val timestamp: String = "",
    val status: Int = -1,
    val errors: Array<FieldMessage>? = null
) : Serializable {
    override fun toString(): String {
        return "ErrorMessage(path='$path', error='$error', message='$message', timestamp='$timestamp', status=$status)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorMessage

        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        return status
    }
}