package com.arainko.nawts.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun String.asColor(): Int = Color.parseColor(this)

fun String.makeToast(context: Context?): Unit = Toast.makeText(context, this, Toast.LENGTH_LONG).show()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.addTo(parent: ViewGroup?) = parent?.addView(this)