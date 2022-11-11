package com.innovation.mx.models

data class ResponseModel(
    val status: String,
    val data: List<EmployeeModel>?
)