package com.julianozanella.springandroidapp.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PagamentoDTO(val numeroDeParcelas: Int, @SerializedName("@type") val type: String) : Serializable