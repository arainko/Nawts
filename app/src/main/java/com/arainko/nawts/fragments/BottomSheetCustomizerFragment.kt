package com.arainko.nawts.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arainko.nawts.R
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.removeTrailingLines
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.note_layout.view.*
import kotlin.properties.Delegates

class BottomSheetCustomizerFragment() : BottomSheetDialogFragment() {

    private lateinit var note: Note
    private lateinit var adapter: NoteAdapter
    private var position: Int by Delegates.notNull()

    constructor(note: Note, adapter: NoteAdapter, position: Int) : this() {
        this.note = note
        this.adapter = adapter
        this.position = position
    }

    private val model: NoteViewModel by viewModels()
    private lateinit var currentBackgroundColor: String
    private lateinit var currentStrokeColor: String
    private lateinit var colorToButtonMap: Map<String, MaterialButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!this::note.isInitialized) dismissAllowingStateLoss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            currentBackgroundColor = note.style.backgroundColor
            currentStrokeColor = note.style.strokeColor
            val previewHeader = note.header.removeTrailingLines()
            val previewContent = note.content.removeTrailingLines()

            cardPreview.apply {
                cardHeader.text = previewHeader
                cardText.text = previewContent
                (this as MaterialCardView).setCardBackgroundColor(currentBackgroundColor.asIntColor())
            }

            colorToButtonMap = resources.getStringArray(R.array.colors).map {
                it to layoutInflater
                    .inflate(R.layout.color_button_layout, scrollContainer, false) as MaterialButton
            }.toMap()

            colorToButtonMap.forEach { (hexColor, button) ->
                button.run {
                    addTo(scrollContainer)
                    setBackgroundColor(hexColor.asIntColor())
                    setOnClickListener {
                        updateButtonIcons(currentBackgroundColor, hexColor)
                        (cardPreview as MaterialCardView).setCardBackgroundColor(hexColor.asIntColor())
                        currentBackgroundColor = hexColor
                    }
                }
            }

            colorToButtonMap[currentBackgroundColor]?.apply {
                strokeWidth = 10
            }

    }

    override fun onDestroy() {
        super.onDestroy()
            note.style.backgroundColor = currentBackgroundColor
            adapter.notifyItemChanged(position)
            model.updateNote(note)
    }

    private fun updateButtonIcons(oldColor: String, newColor: String) {
        colorToButtonMap[oldColor]?.strokeWidth = 0
        colorToButtonMap[newColor]?.strokeWidth = 10
    }

}