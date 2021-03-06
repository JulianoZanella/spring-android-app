package com.julianozanella.springandroidapp.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import com.julianozanella.springandroidapp.extensions.isValid
import com.julianozanella.springandroidapp.extensions.isValidEmail
import com.julianozanella.springandroidapp.extensions.validate
import com.julianozanella.springandroidapp.view.adapter.CidadesAdapter
import com.julianozanella.springandroidapp.view.adapter.EstadosAdapter
import com.julianozanella.springandroidapp.viewModel.SignupViewModel
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : FormActivity() {

    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        viewModel = ViewModelProviders.of(this)[SignupViewModel::class.java]
        supportActionBar?.title = getString(R.string.signup)
        setResult(Activity.RESULT_CANCELED)
        fillSpinners()
        bt_do_signup.setOnClickListener { mainAction() }
        validate()
        et_fone_3.setOnEditorActionListener(this)
    }

    private fun fillSpinners() {
        val statesAdapter = EstadosAdapter(this)
        sp_states.adapter = statesAdapter
        val cidadesAdapter = CidadesAdapter(this)
        sp_cities.adapter = cidadesAdapter
        viewModel.getEstados().observe(this, Observer {
            if (it != null) {
                statesAdapter.items = it
            }
        })
        sp_states.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setEstado(id.toString())
            }
        }
        viewModel.getCidades().observe(this, Observer { if (it != null) cidadesAdapter.items = it })
    }

    private fun validate() {
        et_name.validate({
            et_name.text.toString().isValid(5, 120)
        }, getString(R.string.invalid_length, 5, 120))
        et_email_signup.validate({
            et_email_signup.text.toString().isValidEmail()
        }, getString(R.string.invalid_email))
        et_cpf_or_cnpj.validate({
            et_cpf_or_cnpj.text.toString().isValid(11, 14)
        }, getString(R.string.invalid_length, 11, 14))
        et_password_signup.validate({
            et_password_signup.text.toString().isValid()
        }, getString(R.string.invalid_required))
        et_street.validate({
            et_street.text.toString().isValid()
        }, getString(R.string.invalid_required))
        et_number.validate({
            et_number.text.toString().isValid()
        }, getString(R.string.invalid_required))
        et_cep.validate({
            et_cep.text.toString().isValid()
        }, getString(R.string.invalid_required))
        et_fone_1.validate({
            et_fone_1.text.toString().isValid()
        }, getString(R.string.invalid_required))
    }

    override fun mainAction(view: View?) {
        val clienteNewDTO = ClienteNewDTO()
        clienteNewDTO.let {
            it.nome = et_name.text.toString()
            it.email = et_email_signup.text.toString()
            it.tipo = if (rg_client_type.checkedRadioButtonId == R.id.rb_simple_person) 1 else 2
            it.cpfOuCnpj = et_cpf_or_cnpj.text.toString()
            it.senha = et_password_signup.text.toString()
            it.logradouro = et_street.text.toString()
            it.numero = et_number.text.toString()
            it.bairro = et_neighborhood.text.toString()
            it.cep = et_cep.text.toString()
            it.telefone1 = et_fone_1.text.toString()
            it.telefone2 = et_fone_2.text.toString()
            it.telefone3 = et_fone_3.text.toString()
            it.cidadeId = sp_cities.selectedItemId.toString()
        }
        insert(clienteNewDTO)
    }

    private fun insert(clienteNewDTO: ClienteNewDTO) {
        pb_signup.visibility = View.VISIBLE
        viewModel.insert(clienteNewDTO).observe(this, Observer {
            if (it != null) showDialog(clienteNewDTO)
        })
    }

    private fun showDialog(clienteNewDTO: ClienteNewDTO) {
        pb_signup.visibility = View.GONE
        val alert = AlertDialog.Builder(this).create()
        alert.setTitle(getString(R.string.message_tittle_success))
        alert.setMessage(getString(R.string.message_success_save))
        alert.setCancelable(false)
        alert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { _, _ ->
            login(clienteNewDTO)
        }
        alert.show()
    }

    private fun login(obj: ClienteNewDTO) {
        val intent = Intent()
        intent.putExtra(EXTRA_CLIENT, CredenciaisDTO(obj.email, obj.senha))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_CLIENT = "Client"
        const val REQUEST_CODE = 145
    }
}
