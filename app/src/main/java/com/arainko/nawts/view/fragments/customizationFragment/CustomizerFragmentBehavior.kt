package com.arainko.nawts.view.fragments.customizationFragment

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.blendARGB
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.elements.NoteHolder
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*

class CustomizerFragmentBehavior(
    fragment: CustomizerFragment,
    private val modelActions: NoteViewModel,
    val noteHolder: NoteHolder,
    val adapter: NoteAdapter
) : FragmentUIBehavior<CustomizerFragment>(fragment) {

    val note = noteHolder.note
    internal var currentBackgroundColor: String = note.style.backgroundColor
    internal var currentStrokeColor: String = note.style.strokeColor


    val onColorButtonClickListener = View.OnClickListener {
        val hexColor = it.tag as String
        updateColorButtonIcon(fragment.colorToButtonMap, currentBackgroundColor, hexColor)
        updatePreviewBackgroundColor(hexColor)
        currentBackgroundColor = hexColor
    }

    val onColorButtonLongClickListener = View.OnLongClickListener {
        val hexColor = it.tag as String
        updateColorButtonStrokes(fragment.colorToButtonMap, currentStrokeColor, hexColor)
        updatePreviewStrokeColor(hexColor)
        currentStrokeColor = hexColor
        true
    }

    private fun updateColorButtonStrokes(
        colorToButtonMap: Map<String, MaterialButton>,
        oldColor: String,
        newColor: String
    ) {
        colorToButtonMap[oldColor]?.strokeWidth = 0
        colorToButtonMap[newColor]?.strokeWidth = 10
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
        adapter.notifyItemChanged(noteHolder.adapterPosition)
        modelActions.updateNotes(note)
    }

}