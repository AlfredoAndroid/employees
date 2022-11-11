package com.innovation.mx.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.innovation.mx.models.EmployeeModel

fun hideKeyboard(activity: Activity) {
    activity.currentFocus?.let {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}

object CurrentEmployee {
    var employee: EmployeeModel? = null
}