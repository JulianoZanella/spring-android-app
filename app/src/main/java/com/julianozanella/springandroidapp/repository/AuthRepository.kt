package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.CredenciaisDTO

class AuthRepository : Repository() {


    private val service = RetrofitConfig().getAuthService()

    fun authenticate(credenciaisDTO: CredenciaisDTO): LiveData<String> =
        getLiveDataFromHeader(service.authenticate(credenciaisDTO), HEADER.AUTHORIZATION)

    fun refreshToken(): LiveData<String> = getLiveDataFromHeader(service.refreshToken(), HEADER.AUTHORIZATION)

    companion object {

        private var INSTANCE: AuthRepository? = null

        fun getInstance(): AuthRepository {
            if (INSTANCE == null) {
                INSTANCE = AuthRepository()
            }
            return INSTANCE!!
        }
    }
}