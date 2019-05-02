package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.config.RetrofitConfig
import com.julianozanella.springandroidapp.dto.ClienteDTO
import com.julianozanella.springandroidapp.dto.ClienteNewDTO
import com.julianozanella.springandroidapp.service.webService.ClientService
import okhttp3.MultipartBody

class ClientRepository : Repository() {

    private val service: ClientService = RetrofitConfig().getClientService()
    private val profileService = RetrofitConfig().getProfileService()

    fun findById(id: String): LiveData<ClienteDTO> = getLiveData(service.findById(id))

    fun findByEmail(id: String): LiveData<ClienteDTO> = getLiveData(service.findByEmail(id))

    fun insert(clienteNewDTO: ClienteNewDTO): LiveData<String> =
        getLiveDataFromHeader(service.insert(clienteNewDTO), HEADER.LOCATION)

    fun uploadPicture(body: MultipartBody.Part): LiveData<String> =
        getLiveDataFromHeader(profileService.uploadImage(body), HEADER.LOCATION)


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