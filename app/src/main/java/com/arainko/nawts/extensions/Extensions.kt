package com.arainko.nawts.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.graphics.ColorUtils
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.arainko.nawts.extensions.delegates.DataBindings
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun String.asIntColor(): Int = Color.parseColor(this)

fun @receiver:ColorInt Int.blendARGB(@ColorInt blendWith: Int, ratio: Float) =
    ColorUtils.blendARGB(this, blendWith, ratio)

fun String.makeToast(context: Context?, isLong: Boolean = true) {
    val toastLength = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(context, this, toastLength).show()
}

fun String.removeTrailingLines() = if (this.lines().size > 1) this.lines()[0] + "\n..."
    else this.lines()[0]

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.addTo(parent: ViewGroup?) = parent?.addView(this)



