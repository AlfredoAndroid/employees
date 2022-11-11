package com.innovation.mx.ui.login

import com.innovation.mx.api.ApiService
import com.innovation.mx.models.ResponseSimpleModel
import io.reactivex.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getEmployee(id: Long): Single<ResponseSimpleModel> {
        return apiService.getEmployee(id)
    }
}