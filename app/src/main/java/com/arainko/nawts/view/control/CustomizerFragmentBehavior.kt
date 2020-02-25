package com.arainko.nawts.view.control

import android.view.View
import android.widget.PopupMenu
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.view.containters.BottomSheetCustomizerFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.elements.NoteAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*

class CustomizerFragmentBehavior(
    fragment: BottomSheetCustomizerFragment,
    private val modelActions: NoteViewModel,
    internal val note: Note,
    private val adapter: NoteAdapter,
    private val adapterPosition: Int
) : FragmentUIBehavior<BottomSheetCustomizerFragment>(fragment) {

    internal var currentBackgroundColor: String = note.style.backgroundColor
    internal var currentStrokeColor: String = note.style.strokeColor

    val onColorButtonClickListener = View.OnClickListener {
        val hexColor = it.tag as String
        updateColorButtonStrokes(fragment.colorToButtonMap, currentBackgroundColor, hexColor)
        updatePreviewBackgroundColor(hexColor)
        currentBackgroundColor = hexColor
    }

    val onColorButtonLongClickListener = View.OnLongClickListener {
        val hexColor = it.tag as String
        updatePreviewStrokeColor(hexColor)
        currentStrokeColor = hexColor
        true
    }

    private val onResetCustomizationMenuClickListener = PopupMenu.OnMenuItemClickListener {
        when(it.itemId) {
            R.id.defaultBackgroundColor -> {
                updatePreviewBackgroundColor("#ffffff")
                updateColorButtonStrokes(fragment.colorToButtonMap, currentBackgroundColor, "#ffffff")
                currentBackgroundColor = "#ffffff"
                true
            }
            R.id.defaultStrokeColor -> {
                updatePreviewStrokeColor("#00000000")
                currentStrokeColor = "#00000000"
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

    private fun updatePreviewBackgroundColor(hexColor: String) {
        (fragment.cardPreview as MaterialCardView)
            .setCardBackgroundColor(hexColor.asIntColor())
    }

    private fun updatePreviewStrokeColor(hexColor: String) {
        (fragment.cardPreview as MaterialCardView)
            .strokeColor = hexColor.asIntColor()
    }

    fun commitNoteChanges() {
        note.style.backgroundColor = currentBackgroundColor
        note.style.strokeColor = currentStrokeColor
        adapter.notifyItemChanged(adapterPosition)
        modelActions.updateNote(note)
    }

}