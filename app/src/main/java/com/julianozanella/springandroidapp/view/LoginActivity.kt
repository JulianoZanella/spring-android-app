package com.julianozanella.springandroidapp.view

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import com.julianozanella.springandroidapp.extensions.isValid
import com.julianozanella.springandroidapp.extensions.isValidEmail
import com.julianozanella.springandroidapp.extensions.validate
import com.julianozanella.springandroidapp.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : FormActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this)[AuthViewModel::class.java]
        if (!getToken().isNullOrBlank()) {
            showProgressBar(true)
            viewModel.refreshToken().observe(this, Observer {
                if (it != null) {
                    authComplete(it)
                }
            })
        }else{
            showFields()
            et_email.validate({
                et_email.text.toString().isValidEmail()
            }, getString(R.string.invalid_email))
            et_password.validate({
                et_password.text.toString().isValid()
            }, getString(R.string.invalid_required))
            bt_login.setOnClickListener { mainAction() }
            bt_signup.setOnClickListener {
                startActivityForResult(
                    Intent(this, SignupActivity::class.java),
                    SignupActivity.REQUEST_CODE
                )
            }
            et_password.setOnEditorActionListener(this)
        }
    }

    private fun showFields() {
        et_email.visibility = View.VISIBLE
        et_password.visibility = View.VISIBLE
        bt_login.visibility = View.VISIBLE
        bt_signup.visibility = View.VISIBLE
    }

    override fun mainAction(view: View?) {
        val email: String = et_email.text.toString()
        val psw: String = et_password.text.toString()
        authenticate(CredenciaisDTO(email, psw))
    }

    private fun authenticate(obj: CredenciaisDTO) {
        if (isEmailValid(obj.email) && isPasswordValid(obj.senha)) {
            showProgressBar(true)
            viewModel.authenticate(obj).observe(this, Observer {
                if (it != null) authComplete(it)
            })
        } else {
            showProgressBar(false)
            Toast.makeText(this, getString(R.string.msg_invalid_email_or_password), Toast.LENGTH_SHORT).show()
        }
    }

    private fun authComplete(token: String) {
        showProgressBar(false)
        viewModel.successfullLogin(token)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@") && email.length > 5
    }

    private fun isPasswordValid(psw: String): Boolean {
        return psw.length >= 3
    }

    private fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SignupActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val credenciaisDTO: CredenciaisDTO =
                    data.getSerializableExtra(SignupActivity.EXTRA_CLIENT) as CredenciaisDTO
                authenticate(credenciaisDTO)
            }
        }
    }
}
