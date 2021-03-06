package com.julianozanella.springandroidapp.view

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.domain.ErrorMessage
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.getSharedPreference
import com.julianozanella.springandroidapp.extensions.resetPreferences

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenString = getSharedPreference(KEY.TOKEN, String::class.java) as String?
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        HandleErrorAsync = HandleErrorAsync()
    }

    fun logout() {
        resetPreferences()
        tokenString = null
        RetrofitConfig().recreateRetrofit()
    }

    companion object {
        private var tokenString: String? = null
        private var HandleErrorAsync: HandleErrorAsync? = null

        fun getToken(): String? {
            return tokenString
        }

        fun handleError(error: ErrorMessage) {
            HandleErrorAsync?.execute(error)
        }
    }

    private inner class HandleErrorAsync : AsyncTask<ErrorMessage, Void, ErrorMessage>() {

        override fun doInBackground(vararg params: ErrorMessage?): ErrorMessage? {
            return params[0]
        }

        override fun onPostExecute(result: ErrorMessage) {
            super.onPostExecute(result)
            when (result.status) {
                -141 -> {
                    showDialog(
                        getString(R.string.connection_error_title),
                        getString(R.string.server_error)
                    )
                }
                403 -> {
                    showDialog(
                        getString(R.string.default_error_title, result.status),
                        getString(R.string.refresh_token_error)
                    )
                }
                401 -> {
                    showDialog(
                        getString(R.string.autenticate_error_title),
                        getString(R.string.autenticate_error_message)
                    )
                }
                422 -> {
                    var msg = ""
                    result.errors?.forEach {
                        msg += "${it.fieldName.toUpperCase()}: ${it.message}\n"
                    }
                    showDialog(getString(R.string.validation_error_title), msg)
                }
                else -> {
                    showDialog(getString(R.string.default_error_title, result.status), result.message)
                }
            }
        }

        private fun showDialog(title: String, msg: String) {
            val dialog = AlertDialog.Builder(this@BaseActivity).create()
            dialog.setTitle(title)
            dialog.setMessage(msg)
            dialog.setCancelable(false)
            dialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                this@BaseActivity.getString(android.R.string.ok)
            ) { _, _ ->
                dialog.dismiss()
            }
            dialog.show()
        }

    }

}