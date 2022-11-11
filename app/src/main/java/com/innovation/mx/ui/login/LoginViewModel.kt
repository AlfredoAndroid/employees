package com.innovation.mx.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innovation.mx.base.BaseViewModel
import com.innovation.mx.ui.home.HomeRepository
import com.innovation.mx.utils.AndroidSharedPreferences
import com.innovation.mx.utils.CurrentEmployee
import com.innovation.mx.utils.SHARED_PREFERENCES_SESSION_STATE
import com.innovation.mx.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val sharedPreferences: AndroidSharedPreferences
) : BaseViewModel() {

    private val _loginState = MutableLiveData<LoginActions>()

    fun getAction(): LiveData<LoginActions> = _loginState

    fun validateUserId(id: Long) {
        disposable.add(
            repository.getEmployee(id).applySchedulers()
                .subscribe(
                    {
                        _loginState.value = if (it.data == null) {
                            LoginActions.LoginNotFound
                        } else {
                            CurrentEmployee.employee = it.data
                            registryLogin()
                            LoginActions.LoginSuccess
                        }
                    },
                    {
                        it.printStackTrace()
                        _loginState.value = LoginActions.LoginError(it.message.toString())
                    }
                )
        )
    }

    private fun registryLogin() {
        sharedPreferences.putValue(SHARED_PREFERENCES_SESSION_STATE, true)
    }

}