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
    private lateinit var previewHeader: String
    private lateinit var previewContent: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentColor = note.color
        previewHeader = note.header.removeTrailingLines()
        previewContent = note.content.removeTrailingLines()

        cardPreview.apply {
            cardHeader.text = previewHeader
            cardText.text = previewContent
            (this as MaterialCardView).setCardBackgroundColor(currentColor.asIntColor())
        }

        val buttonToColorMap = resources.getStringArray(R.array.colors)
            .map { hexColor ->
                val button =
                    layoutInflater.inflate(R.layout.color_button_layout, scrollContainer, false).apply {
                        setBackgroundColor(hexColor.asIntColor())
                        setOnClickListener { TODO() }
                    }
                Pair(button as Button, hexColor)
            }.toMap()

//        resources.getStringArray(R.array.colors).forEach { hex ->
//            layoutInflater.inflate(R.layout.color_button_layout, scrollContainer, false).apply {
//                tag = hex
//                setBackgroundColor(hex.asIntColor())
//                setOnClickListener {
//                    //                    note.color = it.tag.toString()
////                    adapter.notifyItemChanged(position)
//                    (cardPreview as MaterialCardView).setCardBackgroundColor(hex.asIntColor())
//                    currentColor = hex
//                }
//            }.addTo(scrollContainer)
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        note.color = currentColor
        adapter.notifyItemChanged(position)
        model.updateNote(note)
    }

}