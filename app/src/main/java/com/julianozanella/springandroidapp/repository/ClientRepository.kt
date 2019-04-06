package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import com.julianozanella.springandroidapp.service.webService.ClientService
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientRepository {

    private val service: ClientService = RetrofitConfig().getClientService()
    private val profileService = RetrofitConfig().getProfileService()

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

    fun insert(clienteNewDTO: ClienteNewDTO): LiveData<String> {
        val data = MutableLiveData<String>()
        service.insert(clienteNewDTO).enqueue(object : Callback<ClienteNewDTO> {
            override fun onResponse(call: Call<ClienteNewDTO>, response: Response<ClienteNewDTO>) {
                if (response.isSuccessful) data.value = response.headers().get("location")
            }

            override fun onFailure(call: Call<ClienteNewDTO>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }

    fun uploadPicture(body: MultipartBody.Part): LiveData<String> {
        val data = MutableLiveData<String>()
        profileService.uploadImage(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    data.value = response.headers().get("location")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }


    companion object {

        private var INSTANCE: ClientRepository? = null

        fun getInstance(): ClientRepository {
            if (INSTANCE == null) {
                INSTANCE = ClientRepository()
            }
            return INSTANCE as ClientRepository
        }
    }
}