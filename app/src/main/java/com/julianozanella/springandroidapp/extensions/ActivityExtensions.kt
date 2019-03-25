package com.julianozanella.springandroidapp.extensions

import android.content.Context
import com.google.gson.Gson
import com.julianozanella.springandroidapp.domain.Cart

@Throws(IllegalArgumentException::class)
fun Context.saveSharedPreferences(key: KEY, value: Any) {
    val preferences = this.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
    val gson = Gson()
    when (value) {
        is String -> preferences.edit().putString(key.name, value).apply()
        is Int -> preferences.edit().putInt(key.name, value).apply()
        is Float -> preferences.edit().putFloat(key.name, value).apply()
        is Cart -> preferences.edit().putString(key.name, gson.toJson(value)).apply()
        else -> throw IllegalArgumentException()
    }
}

fun Context.getSharedPreference(key: KEY, type: Class<out Any>): Any? {
    val preferences = this.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
    val gson = Gson()
    return when (type.getConstructor().newInstance()) {
        is String -> preferences.getString(key.name, null)
        is Int -> preferences.getInt(key.name, -1)
        is Float -> preferences.getFloat(key.name, -1F)
        is Cart -> gson.fromJson(preferences.getString(key.name, null), Cart::class.java)
        else -> null
    }
}

fun Context.resetPreferences() {
    val preferences = this.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
    preferences.edit().clear().commit()
}

private const val ARQUIVO = "springAndroidSharedPreferences"

enum class KEY {
    CART, TOKEN, EMAIL
}