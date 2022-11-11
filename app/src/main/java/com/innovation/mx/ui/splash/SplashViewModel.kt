package com.innovation.mx.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innovation.mx.base.BaseViewModel
import com.innovation.mx.utils.AndroidSharedPreferences
import com.innovation.mx.utils.Flows
import com.innovation.mx.utils.SHARED_PREFERENCES_SESSION_STATE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: AndroidSharedPreferences
) : BaseViewModel() {
    private val _flow = MutableLiveData<Flows>()

    var flow: LiveData<Flows> = _flow

    fun verifyLoginState() {
        val isLogged = sharedPreferences.getBoolean(SHARED_PREFERENCES_SESSION_STATE)
        Handler(Looper.getMainLooper()).postDelayed( {
//            _flow.value = if (isLogged) {
//                Flows.HOME
//            } else {
//                Flows.LOGIN
//            }
            _flow.value = Flows.LOGIN
        }, 2000)
    }
}