package com.julianozanella.springandroidapp.service

import android.content.Context
import com.auth0.android.jwt.JWT
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.extensions.KEY
import com.julianozanella.springandroidapp.extensions.resetPreferences
import com.julianozanella.springandroidapp.extensions.saveSharedPreferences

class AuthService(private val context: Context) {

    fun successfullLogin(token: String) {
        val tok = token.substring(7)//remove bearer
        val jwt = JWT(tok)
        if (jwt.subject != null) {
            context.saveSharedPreferences(KEY.TOKEN, token)
            context.saveSharedPreferences(KEY.EMAIL, jwt.subject!!)
        }
    }

    fun logout() {
        context.resetPreferences()
        RetrofitConfig().recreateRetrofit()
    }
}