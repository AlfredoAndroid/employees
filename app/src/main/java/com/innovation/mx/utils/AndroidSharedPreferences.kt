package com.innovation.mx.utils

import android.content.SharedPreferences
import javax.inject.Inject

class AndroidSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun putValue(key: String, data: Boolean) = sharedPreferences.edit().putBoolean(key, data).apply()
    fun putValue(key: String, data: String) = sharedPreferences.edit().putString(key, data).apply()
    fun putValue(key: String, data: Int) = sharedPreferences.edit().putInt(key, data).apply()
    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)
    fun getString(key: String) = sharedPreferences.getString(key, "")
    fun getInt(key: String) = sharedPreferences.getInt(key, -1)
    fun deleteString(data: String) = sharedPreferences.edit().remove(data).apply()
}