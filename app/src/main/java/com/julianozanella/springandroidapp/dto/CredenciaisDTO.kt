package com.julianozanella.springandroidapp.dto

import java.io.Serializable

data class CredenciaisDTO(val email: String = "", val senha: String = "") : Serializable {
}