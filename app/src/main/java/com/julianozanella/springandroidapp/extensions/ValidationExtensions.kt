package com.julianozanella.springandroidapp.extensions

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText

private fun EditText.afterTextChanged(invokeValidation: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(content: Editable?) {
            invokeValidation(content.toString())
        }

        override fun beforeTextChanged(
            content: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            content: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
        }
    })
}

fun EditText.validate(
    validator: (String) -> Boolean,
    message: String
) {

    this.afterTextChanged {
        this.error =
            if (validator(it))
                null
            else
                message
    }
}

fun String.isValid(): Boolean {
    return this.isNotEmpty()
}

fun String.isValid(minLength: Int, maxLength: Int): Boolean {
    return this.isNotEmpty() && this.length in minLength until (maxLength +1)
}

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()