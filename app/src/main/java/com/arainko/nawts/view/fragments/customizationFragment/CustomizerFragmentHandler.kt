package com.arainko.nawts.view.fragments.customizationFragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.arainko.nawts.R
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.blendARGB
import com.arainko.nawts.view.abstracts.FragmentHandler
import com.arainko.nawts.view.fragments.homeFragment.HomeFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_customizer.*

class CustomizerFragmentHandler(fragment: CustomizerFragment) : FragmentHandler<CustomizerFragment>(fragment) {

    val note = fragment.noteViewModel.sharedNote!!
    internal var currentBackgroundColor: String = note.style.backgroundColor
    internal var currentStrokeColor: String = note.style.strokeColor

    init {
        fragment.customizerViewModel.run {
            backgroundColor.value = note.style.backgroundColor
        }
    }


    val onColorButtonClickListener = View.OnClickListener {
        val hexColor = it.tag as String
        updateColorButtonIcon(fragment.colorToButtonMap, currentBackgroundColor, hexColor)
        updatePreviewBackgroundColor(hexColor)
        currentBackgroundColor = hexColor
    }

    private fun updateColorButtonIcon(
        colorToButtonMap: Map<String, MaterialButton>,
        oldColor: String,
        newColor: String
    ) {
        val updatedButton = colorToButtonMap[newColor]
        colorToButtonMap[oldColor]?.icon = null
        updatedButton?.icon = ContextCompat.getDrawable(updatedButton?.context!!, R.drawable.ic_color_check)
    }

    // TODO: migrate to BindingAdapter
    private fun updatePreviewBackgroundColor(newColor: String) {
        val colorFrom = (fragment.customizerRoot.background as ColorDrawable).color
        val colorTo = newColor.asIntColor()
            .blendARGB(Color.BLACK, 0.4f)
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
            duration = 300
            addUpdateListener {
                fragment.customizerRoot.setBackgroundColor(it.animatedValue as Int)
            }
        }

        fragment.customizerRoot.tag = colorAnimator

        colorAnimator.start()
    }

    private fun updatePreviewStrokeColor(hexColor: String) {
//        (fragment.cardPreview as MaterialCardView)
//            .strokeColor = hexColor.asIntColor()
    }

    fun commitNoteChanges() {
        note.style.backgroundColor = currentBackgroundColor
        note.style.strokeColor = currentStrokeColor
        (fragment.parentFragment as HomeFragment)
            .noteAdapter
            .notifyItemChanged(fragment.noteViewModel.sharedNoteAdapterPosition!!)

    }

}