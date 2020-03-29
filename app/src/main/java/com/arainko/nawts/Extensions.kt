package com.arainko.nawts

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

class dataBindings <out T : ViewDataBinding> (
    @LayoutRes private val resId: Int
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    override operator fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T = binding ?: createBinding(thisRef).also { binding = it }

    private fun createBinding(
        activity: Fragment
    ): T = DataBindingUtil.inflate(LayoutInflater.from(activity.context),resId,null,true)
}