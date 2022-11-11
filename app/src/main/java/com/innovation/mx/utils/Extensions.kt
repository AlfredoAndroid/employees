package com.innovation.mx.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Activity.getView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.showMessage(msg: String) {
    Snackbar.make(getView(), msg, 3000).show()
}

fun Long?.orZero(): Long = this ?: 0