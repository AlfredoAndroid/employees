package com.innovation.mx.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.innovation.mx.ui.main.MainActivity
import com.innovation.mx.ui.login.LoginActivity
import com.innovation.mx.utils.Flows
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity  : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        bindViewModel()
        viewModel.verifyLoginState()
    }

    private fun bindViewModel() {
        viewModel.flow.observe(this, Observer(this::nextFlow))
    }

    private fun nextFlow(flows: Flows) {
        startActivity(
            Intent(
                this,
                if (flows == Flows.LOGIN) LoginActivity::class.java else MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        overridePendingTransition(0, 0)
        finish()
    }
}