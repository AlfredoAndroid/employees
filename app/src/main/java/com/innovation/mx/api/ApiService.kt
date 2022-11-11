package com.innovation.mx.api

import com.innovation.mx.models.ResponseModel
import com.innovation.mx.models.ResponseSimpleModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("employees")
    fun getEmployees(): Single<ResponseModel>

    @GET("employee/{id}")
    fun getEmployee(@Path("id") id: Long): Single<ResponseSimpleModel>
}