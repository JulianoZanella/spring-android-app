package com.julianozanella.springandroidapp.service

import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    fun authenticate(@Body creds: CredenciaisDTO): Call<CredenciaisDTO>

    @POST("auth/refresh_token")
    fun refreshToken(): Call<CredenciaisDTO>
}