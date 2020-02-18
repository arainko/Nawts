package com.arainko.nawts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.arainko.nawts.R
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.removeTrailingLines
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.note_layout.view.*

class BottomSheetCustomizerFragment(
    val note: Note,
    val model: NoteViewModel,
    val adapter: NoteAdapter,
    val position: Int
) : BottomSheetDialogFragment() {

    private lateinit var currentColor: String
    private lateinit var colorToButtonMap: Map<String, MaterialButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentColor = note.color
        val previewHeader = note.header.removeTrailingLines()
        val previewContent = note.content.removeTrailingLines()

        cardPreview.apply {
            cardHeader.text = previewHeader
            cardText.text = previewContent
            (this as MaterialCardView).setCardBackgroundColor(currentColor.asIntColor())
        }

        colorToButtonMap = resources.getStringArray(R.array.colors).map { hexColor ->
            hexColor to layoutInflater.inflate(R.layout.color_button_layout, scrollContainer, false).apply {
                addTo(scrollContainer)
                setBackgroundColor(hexColor.asIntColor())
                setOnClickListener {
                    updateButtonIcons(currentColor, hexColor)
                    (cardPreview as MaterialCardView).setCardBackgroundColor(hexColor.asIntColor())
                    currentColor = hexColor
                }
            } as MaterialButton
        }.toMap()

        val checkmarkDrawable = resources.getDrawable(R.drawable.ic_color_check, null)

        colorToButtonMap[currentColor]?.icon = checkmarkDrawable

    }

    override fun onDestroy() {
        super.onDestroy()
        note.color = currentColor
        adapter.notifyItemChanged(position)
        model.updateNote(note)
    }

    private fun updateButtonIcons(oldColor: String, newColor: String) {
        colorToButtonMap[oldColor]?.strokeWidth = 0
        colorToButtonMap[newColor]?.strokeWidth = 10
    }

}