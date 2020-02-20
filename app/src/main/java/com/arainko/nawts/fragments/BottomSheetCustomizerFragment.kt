package com.arainko.nawts.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.arainko.nawts.R
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.removeTrailingLines
import com.arainko.nawts.fragments.uiBehaviors.CustomizerFragmentBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.ModelActions
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.note_layout.view.*
import kotlinx.android.synthetic.main.note_layout.view.cardHeader
import kotlinx.android.synthetic.main.note_layout.view.cardText
import kotlinx.android.synthetic.main.note_preview_layout.*
import kotlinx.android.synthetic.main.note_preview_layout.cardOverflowButton
import kotlinx.android.synthetic.main.note_preview_layout.view.*
import kotlin.properties.Delegates

class BottomSheetCustomizerFragment() : BottomSheetDialogFragment() {

    lateinit var fragmentBehavior: CustomizerFragmentBehavior
    lateinit var colorToButtonMap: Map<String, MaterialButton>

    constructor(
        modelActions: ModelActions,
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
    ): View? = inflater.inflate(R.layout.bottom_sheet_customization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardPreview.run {
            cardHeader.text = fragmentBehavior.note.header.removeTrailingLines()
            cardText.text = fragmentBehavior.note.content.removeTrailingLines()
            cardOverflowButton.setOnClickListener(fragmentBehavior.onOverflowButtonClickListener)
            (this as MaterialCardView).run {
                strokeColor = fragmentBehavior.currentStrokeColor.asIntColor()
                setCardBackgroundColor(fragmentBehavior.currentBackgroundColor.asIntColor())
            }
        }

        colorToButtonMap = resources.getStringArray(R.array.colors).map {
            it to layoutInflater
                .inflate(R.layout.color_button_layout, scrollContainer, false) as MaterialButton
        }.toMap()

        colorToButtonMap.forEach { (hexColor, button) ->
            button.run {
                tag = hexColor
                addTo(scrollContainer)
                setBackgroundColor(hexColor.asIntColor())
                setOnClickListener(fragmentBehavior.onColorButtonClickListener)
                setOnLongClickListener(fragmentBehavior.onColorButtonLongClickListener)
            }
        }

        colorToButtonMap[fragmentBehavior.currentBackgroundColor]?.strokeWidth = 10

    }

    override fun onPause() {
        super.onPause()
        fragmentBehavior.commitNoteChanges()
    }

}