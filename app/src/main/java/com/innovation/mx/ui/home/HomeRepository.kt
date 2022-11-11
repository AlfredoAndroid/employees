package com.innovation.mx.ui.home

import com.innovation.mx.api.ApiService
import com.innovation.mx.models.ResponseModel
import com.innovation.mx.utils.AndroidSharedPreferences
import io.reactivex.Single
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) {
        fun getEmployees(): Single<ResponseModel> {
        return apiService.getEmployees()
    }
}