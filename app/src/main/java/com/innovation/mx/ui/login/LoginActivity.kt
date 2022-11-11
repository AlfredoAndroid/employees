package com.innovation.mx.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.innovation.mx.ui.main.MainActivity
import com.innovation.mx.R
import com.innovation.mx.databinding.ActivityLoginBinding
import com.innovation.mx.utils.hideKeyboard
import com.innovation.mx.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        bindViewModel()

        binding.apply {
            buttonLogin.setOnClickListener {
                hideKeyboard(this@LoginActivity)
                val id = editTextLogin.text.toString()
                if (id.isEmpty()) {
                    showMessage(getString(R.string.login_empty_number))
                } else {
                    viewModel.validateUserId(id.toLong())
                }
            }
        }
    }

    private fun bindViewModel() {
        viewModel.getAction().observe(this, Observer(this::handleAction))
    }

    private fun handleAction(action: LoginActions) {
        when (action) {
            LoginActions.LoginSuccess -> loginsuccess()
            LoginActions.LoginNotFound -> showMessage(getString(R.string.login_not_found))
            is LoginActions.LoginError -> showMessage(action.msgError)
        }
    }

    private fun loginsuccess() {
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        finish()
    }
}