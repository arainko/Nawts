package com.arainko.nawts.view.fragments

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.arainko.nawts.R
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val defaultColor = resources.getColor(R.color.colorAccent, null)
        animateSystemBarColors(defaultColor)
    }

    fun animateSystemBarColors(@ColorInt colorTo: Int) {
        val colorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            window.statusBarColor,
            colorTo
        ).apply {
            duration = resources.getInteger(R.integer.animationSpeed).toLong()
            addUpdateListener {
                window.statusBarColor = it.animatedValue as Int
                window.navigationBarColor = it.animatedValue as Int
            }
        }
        colorAnimator.start()
    }
}
