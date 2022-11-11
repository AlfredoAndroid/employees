package com.innovation.mx.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innovation.mx.base.BaseViewModel
import com.innovation.mx.models.EmployeeModel
import com.innovation.mx.utils.AndroidSharedPreferences
import com.innovation.mx.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {
    private val _employees = MutableLiveData<List<EmployeeModel>>()
    private val _error = MutableLiveData<String>()

    val employees: LiveData<List<EmployeeModel>> = _employees
    val error: LiveData<String> = _error

    fun getEmployees() {
        disposable.add(
            repository.getEmployees().applySchedulers()
                .subscribe(
                    {
                        _employees.value = it.data.orEmpty()
                    },
                    {
                        it.printStackTrace()
                        _error.value = it.message
                    }
                )
        )
    }
}