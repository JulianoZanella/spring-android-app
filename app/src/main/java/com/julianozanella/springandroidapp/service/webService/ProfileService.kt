package com.julianozanella.springandroidapp.service.webService

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService {

    @Multipart
    @POST("clientes/picture")
    fun uploadImage(@Part file: MultipartBody.Part): Call<ResponseBody>
}