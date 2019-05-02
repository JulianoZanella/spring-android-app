package com.julianozanella.springandroidapp.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.julianozanella.springandroidapp.config.utils.ErrorInteceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class Repository {

    fun <T> getLiveData(
        call: Call<T>
    ): LiveData<T> {
        val data = MutableLiveData<T>()
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                ErrorInteceptor().handleError(call, t)
                data.value = null
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }
        })
        return data
    }

    fun <T> getLiveDataFromHeader(
        call: Call<T>, header: HEADER
    ): LiveData<String> {
        val data = MutableLiveData<String>()
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                ErrorInteceptor().handleError(call, t)
                data.value = null
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    var heads = response.headers().get(header.headString)
                    if (header == HEADER.ID) {
                        heads = extractId(heads)
                    }
                    data.value = heads
                }
            }
        })
        return data
    }

    private fun extractId(location: String?): String? {
        location ?: return null
        val position = location.lastIndexOf("/")
        return location.substring(position + 1, location.length)
    }

    enum class HEADER(val headString: String) {
        AUTHORIZATION("Authorization"),
        LOCATION("location"),
        ID("location");
    }

}