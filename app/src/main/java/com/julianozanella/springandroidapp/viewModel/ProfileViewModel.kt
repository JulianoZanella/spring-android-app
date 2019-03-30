package com.julianozanella.springandroidapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.graphics.Bitmap
import com.julianozanella.springandroidapp.repository.ClientRepository
import com.julianozanella.springandroidapp.service.util.FileUtil

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ClientRepository.getInstance(application)
    private val fileUtil = FileUtil(application)

    fun uploadProfilePicture(bitmap: Bitmap): LiveData<String> {
        val body = fileUtil.createImageBody(bitmap)
        return repository.uploadPicture(body)
    }
}