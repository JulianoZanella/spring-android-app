package com.julianozanella.springandroidapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.title = getString(R.string.signup)
        setResult(Activity.RESULT_CANCELED)
        bt_do_signup.setOnClickListener { signup() }
    }

    private fun signup() {
        val clienteNewDTO = ClienteNewDTO()
        clienteNewDTO.let {
            it.nome = et_name.text.toString()
            it.email = et_email_signup.text.toString()
            it.tipo = if (rg_client_type.checkedRadioButtonId == R.id.rb_simple_person) 1 else 2
            it.cpfOuCnpj = et_cep.text.toString()
            it.senha = et_password_signup.text.toString()
            it.logradouro = et_street.text.toString()
            it.numero = et_number.text.toString()
            it.bairro = et_neighborhood.text.toString()
            it.cep = et_cep.text.toString()
            it.telefone1 = et_fone_1.toString()
            it.telefone2 = et_fone_2.toString()
            it.telefone3 = et_fone_3.toString()
            it.cidadeId = "1"
        }
        insertClient(clienteNewDTO)
    }
/*
    private fun isValid(editText: EditText, min: Int = 0, max: Int = 0, contains: String = ""): String {
        //TODO("Validar")
    }*/

    private fun insertClient(obj: ClienteNewDTO) {
        val intent = Intent()
        intent.putExtra(EXTRA_CLIENT, obj)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_CLIENT = "Client"
    }
}
