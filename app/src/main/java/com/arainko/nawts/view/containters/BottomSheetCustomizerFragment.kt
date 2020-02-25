package com.arainko.nawts.view.containters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arainko.nawts.R
import com.arainko.nawts.addTo
import com.arainko.nawts.asIntColor
import com.arainko.nawts.removeTrailingLines
import com.arainko.nawts.view.control.CustomizerFragmentBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.control.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*
import kotlinx.android.synthetic.main.note_layout.view.cardHeader
import kotlinx.android.synthetic.main.note_layout.view.cardText
import kotlinx.android.synthetic.main.note_preview_layout.view.*

class BottomSheetCustomizerFragment() : BottomSheetDialogFragment() {

    lateinit var fragmentBehavior: CustomizerFragmentBehavior
    lateinit var colorToButtonMap: Map<String, MaterialButton>

    constructor(
        modelActions: NoteViewModel,
        note: Note,
        adapter: NoteAdapter,
        position: Int
    ) : this() {
        fragmentBehavior = CustomizerFragmentBehavior(this, modelActions, note, adapter, position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!this::fragmentBehavior.isInitialized) dismissAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vibrantColors = resources.getStringArray(R.array.vibrant_colors)
        val pastelColors = resources.getStringArray(R.array.pastel_colors)

        cardPreview.run {
            cardHeader.text = fragmentBehavior.note.header.removeTrailingLines()
            cardText.text = fragmentBehavior.note.content.removeTrailingLines()
            cardOverflowButton.setOnClickListener(fragmentBehavior.onOverflowButtonClickListener)
            (this as MaterialCardView).run {
                strokeColor = fragmentBehavior.currentStrokeColor.asIntColor()
                setCardBackgroundColor(fragmentBehavior.currentBackgroundColor.asIntColor())
            }
        }

        colorToButtonMap = vibrantColors.union(pastelColors.toList())
            .mapIndexed { index, hexColor ->
                val parentContainer = if (index < vibrantColors.size) scrollContainerVibrant else scrollContainerPastel
                hexColor to layoutInflater
                    .inflate(R.layout.color_button_layout, parentContainer, false) as MaterialButton
            }.toMap()

        colorToButtonMap.forEach {  (hexColor, button) ->
            button.run {
                tag = hexColor
                setBackgroundColor(hexColor.asIntColor())
                setOnClickListener(fragmentBehavior.onColorButtonClickListener)
                setOnLongClickListener(fragmentBehavior.onColorButtonLongClickListener)
            }
        }

        colorToButtonMap.values.forEachIndexed { index, materialButton ->
            val parentContainer = if (index < vibrantColors.size) scrollContainerVibrant else scrollContainerPastel
            materialButton.addTo(parentContainer)
        }

        colorToButtonMap[fragmentBehavior.currentBackgroundColor]?.strokeWidth = 10

    }

    override fun onPause() {
        super.onPause()
        fragmentBehavior.commitNoteChanges()
    }

}