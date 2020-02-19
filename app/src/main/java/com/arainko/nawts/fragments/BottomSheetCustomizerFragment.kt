package com.arainko.nawts.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
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

class BottomSheetCustomizerFragment() : BottomSheetDialogFragment(), PopupMenu.OnMenuItemClickListener {

    private val model: NoteViewModel by viewModels()
    private lateinit var note: Note
    private lateinit var adapter: NoteAdapter
    private var position: Int by Delegates.notNull()
    private lateinit var currentBackgroundColor: String
    private lateinit var currentStrokeColor: String
    private lateinit var colorToButtonMap: Map<String, MaterialButton>

    constructor(note: Note, adapter: NoteAdapter, position: Int) : this() {
        this.note = note
        this.adapter = adapter
        this.position = position
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!this::note.isInitialized) dismissAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            currentBackgroundColor = note.style.backgroundColor
            currentStrokeColor = note.style.strokeColor
            val previewHeader = note.header.removeTrailingLines()
            val previewContent = note.content.removeTrailingLines()

            cardPreview.run {
                cardHeader.text = previewHeader
                cardText.text = previewContent
                (this as MaterialCardView).setCardBackgroundColor(currentBackgroundColor.asIntColor())
                setOnLongClickListener {
                    PopupMenu(this.context, this).apply {
                        setOnMenuItemClickListener(this@BottomSheetCustomizerFragment)
                        inflate(R.menu.customization_menu)
                        show()
                    }
                    true
                }
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
                        updateButtonStrokes(currentBackgroundColor, hexColor)
                        (cardPreview as MaterialCardView).setCardBackgroundColor(hexColor.asIntColor())
                        currentBackgroundColor = hexColor
                    }
                }
            }

            colorToButtonMap[currentBackgroundColor]?.apply {
                strokeWidth = 10
            }

    }

    override fun onPause() {
        super.onPause()
            note.style.backgroundColor = currentBackgroundColor
            adapter.notifyItemChanged(position)
            model.updateNote(note)
    }

    private fun updateButtonStrokes(oldColor: String, newColor: String) {
        colorToButtonMap[oldColor]?.strokeWidth = 0
        colorToButtonMap[newColor]?.strokeWidth = 10
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.defaultBackgroundColor -> {
            currentBackgroundColor = "#ffffff"
            (cardPreview as MaterialCardView).run {
                setCardBackgroundColor(currentBackgroundColor.asIntColor())
            }
            true
        }
        else -> {
            currentStrokeColor = "#00000000"
            (cardPreview as MaterialCardView).run {
                strokeColor = currentStrokeColor.asIntColor()
            }
            true
        }
    }

}