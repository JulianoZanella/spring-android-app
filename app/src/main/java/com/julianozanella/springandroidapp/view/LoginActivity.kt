package com.julianozanella.springandroidapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import com.julianozanella.springandroidapp.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this)[AuthViewModel::class.java]
        viewModel.refreshToken().observe(this, Observer {
            if (it != null) {
                authComplete(it)
            }
        })
        bt_login.setOnClickListener { login() }
    }

    private fun login() {
        val email: String = et_email.text.toString()
        val psw: String = et_password.text.toString()
        if (isEmailValid(email) && isPasswordValid(psw)) {
            showProgressBar(true)
            val creds = CredenciaisDTO(email, psw)
            viewModel.authenticate(creds).observe(this, Observer {
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
}
