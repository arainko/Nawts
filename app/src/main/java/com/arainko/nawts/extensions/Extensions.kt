package com.arainko.nawts.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager

fun String.asColor(): Int = Color.parseColor(this)

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}