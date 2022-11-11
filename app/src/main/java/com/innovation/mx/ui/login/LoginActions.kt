package com.innovation.mx.ui.login

sealed class LoginActions {
    object LoginSuccess : LoginActions()
    object LoginNotFound : LoginActions()
    data class LoginError(val msgError: String) : LoginActions()
}
