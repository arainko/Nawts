package com.arainko.nawts.extensions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*

@BindingAdapter("android:isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    if (this is BottomAppBar) {
        if (isVisible) this.performShow()
        else this.performHide()
    }
    if (this is FloatingActionButton) {
        if (isVisible) this.show()
        else this.hide()
    }
}

@BindingAdapter("android:animateBackgroundColorChangeTo")
fun animateColorChangeTo(view: View, @ColorInt newColor: Int) {
    val colorFrom = (view.background as ColorDrawable).color
    val colorTo = newColor
        .blendARGB(Color.BLACK, 0.4f)
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
        duration = 300
        addUpdateListener {
            view.setBackgroundColor(it.animatedValue as Int)
        }
    }

    view.tag = colorAnimator
    colorAnimator.start()
}