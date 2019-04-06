package com.julianozanella.springandroidapp.domain

import java.io.Serializable


class FieldMessage(
    val fieldName: String = "",
    val message: String = ""
) : Serializable