package com.arainko.nawts.view.containters

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.arainko.nawts.R
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidThreeTen.init(this)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    fun animateStatusBarColor(@ColorInt colorTo: Int) {
        val colorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            window.statusBarColor,
            colorTo
        ).apply {
            duration = resources.getInteger(R.integer.animationSpeed).toLong()
            addUpdateListener {
                window.statusBarColor = it.animatedValue as Int
            }
        }
        colorAnimator.start()
    }
}
