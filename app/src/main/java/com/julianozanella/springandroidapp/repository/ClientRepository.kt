package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.service.webService.ClientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientRepository(context: Context) {

    private val service: ClientService = RetrofitConfig(context).getClientService()

    fun findById(id: String): LiveData<ClienteDTO> {
        val data = MutableLiveData<ClienteDTO>()
        service.findById(id).enqueue(object : Callback<ClienteDTO> {
            override fun onFailure(call: Call<ClienteDTO>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<ClienteDTO>, response: Response<ClienteDTO>) {
                if (response.isSuccessful) data.value = response.body()
            }
        })

        return data
    }

    fun findByEmail(id: String): LiveData<ClienteDTO> {
        val data = MutableLiveData<ClienteDTO>()
        service.findByEmail(id).enqueue(object : Callback<ClienteDTO> {
            override fun onFailure(call: Call<ClienteDTO>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<ClienteDTO>, response: Response<ClienteDTO>) {
                if (response.isSuccessful) data.value = response.body()
            }
        })

        return data
    }


    companion object {

        private var INSTANCE: ClientRepository? = null

        fun getInstance(context: Context): ClientRepository {
            if (INSTANCE == null) {
                INSTANCE = ClientRepository(context)
            }
            return INSTANCE as ClientRepository
        }
    }
}