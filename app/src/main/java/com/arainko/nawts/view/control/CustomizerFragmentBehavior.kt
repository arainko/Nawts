package com.arainko.nawts.view.control

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.view.containters.BottomSheetCustomizerFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.elements.NoteHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*

class CustomizerFragmentBehavior(
    fragment: BottomSheetCustomizerFragment,
    private val modelActions: NoteViewModel,
    val noteHolder: NoteHolder,
    val adapter: NoteAdapter
) : FragmentUIBehavior<BottomSheetCustomizerFragment>(fragment) {

    val note = noteHolder.note
    internal var currentBackgroundColor: String = note.style.backgroundColor
    internal var currentStrokeColor: String = note.style.strokeColor


    val onColorButtonClickListener = View.OnClickListener {
        val hexColor = it.tag as String
        updateColorButtonIcon(fragment.colorToButtonMap, currentBackgroundColor, hexColor)
        updatePreviewBackgroundColor(currentBackgroundColor, hexColor)
        currentBackgroundColor = hexColor


    }

    val onColorButtonLongClickListener = View.OnLongClickListener {
        val hexColor = it.tag as String
        updateColorButtonStrokes(fragment.colorToButtonMap, currentStrokeColor, hexColor)
        updatePreviewStrokeColor(hexColor)
        currentStrokeColor = hexColor
        true
    }

    private val onResetCustomizationMenuClickListener = PopupMenu.OnMenuItemClickListener {
        when(it.itemId) {
            R.id.defaultBackgroundColor -> {
                val defaultBackgroundHex = "#ffffff"
                updatePreviewBackgroundColor(currentBackgroundColor, defaultBackgroundHex)
                updateColorButtonIcon(fragment.colorToButtonMap, currentBackgroundColor, defaultBackgroundHex)
                currentBackgroundColor = defaultBackgroundHex
                true
            }
            R.id.defaultStrokeColor -> {
                val defaultStrokeHex = "#00000000"
                updatePreviewStrokeColor(defaultStrokeHex)
                updateColorButtonStrokes(fragment.colorToButtonMap, currentStrokeColor, defaultStrokeHex)
                currentStrokeColor = defaultStrokeHex
                true
            }
            else -> false
        }
    }

    val onOverflowButtonClickListener = View.OnClickListener {
        PopupMenu(it.context, it).apply {
            inflate(R.menu.customization_menu)
            setOnMenuItemClickListener(onResetCustomizationMenuClickListener)
        }.show()
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

    private fun updatePreviewBackgroundColor(oldColor: String, newColor: String) {
        val colorFrom = oldColor.asIntColor()
        val colorTo = newColor.asIntColor()
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo).apply {
            duration = 300
            addUpdateListener {
                (fragment.cardPreview as MaterialCardView).setCardBackgroundColor(it.animatedValue as Int)
            }
        }

        fragment.cardPreview.tag = colorAnimator

        colorAnimator.start()
    }

    private fun updatePreviewStrokeColor(hexColor: String) {
        (fragment.cardPreview as MaterialCardView)
            .strokeColor = hexColor.asIntColor()
    }

    fun commitNoteChanges() {
        note.style.backgroundColor = currentBackgroundColor
        note.style.strokeColor = currentStrokeColor
        adapter.notifyItemChanged(noteHolder.adapterPosition)
        modelActions.updateNote(note)
    }

}