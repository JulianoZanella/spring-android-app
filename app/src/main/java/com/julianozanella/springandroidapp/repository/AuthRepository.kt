package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(context: Context) {

    private val service = RetrofitConfig(context).getAuthService()

    fun authenticate(credenciaisDTO: CredenciaisDTO): LiveData<String> {
        val data = MutableLiveData<String>()
        service.authenticate(credenciaisDTO).enqueue(object : Callback<CredenciaisDTO> {
            override fun onFailure(call: Call<CredenciaisDTO>, t: Throwable) {
                Log.d("Error", "Erro retrofit:\n${t.message}")
                data.value = null
            }

            override fun onResponse(call: Call<CredenciaisDTO>, response: Response<CredenciaisDTO>) {
                val token = response.headers().get("Authorization")
                data.value = token
            }
        })
        return data
    }

    fun refreshToken(): LiveData<String> {
        val data = MutableLiveData<String>()
        service.refreshToken().enqueue(object : Callback<CredenciaisDTO> {
            override fun onFailure(call: Call<CredenciaisDTO>, t: Throwable) {
                Log.d("Error", "Erro retrofit:\n${t.message}")
                data.value = null
            }

            override fun onResponse(call: Call<CredenciaisDTO>, response: Response<CredenciaisDTO>) {
                val token = response.headers().get("Authorization")
                data.value = token
            }
        })
        return data
    }


    companion object {
        private var INSTANCE: AuthRepository? = null

        fun getInstance(context: Context): AuthRepository {
            if (INSTANCE == null) {
                INSTANCE = AuthRepository(context)
            }
            return INSTANCE!!
        }
    }
}