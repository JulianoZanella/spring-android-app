package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.julianozanella.springandroidapp.dto.CredenciaisDTO
import com.julianozanella.springandroidapp.repository.AuthRepository
import com.julianozanella.springandroidapp.service.AuthService

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository.getInstance()
    private val localService = AuthService(application)

    fun authenticate(obj: CredenciaisDTO): LiveData<String> {
        logout()
        return repository.authenticate(obj)
    }

    fun refreshToken() = repository.refreshToken()

    fun successfullLogin(token: String) {
        localService.successfullLogin(token)
    }

    fun logout() {
        localService.logout()
    }
}