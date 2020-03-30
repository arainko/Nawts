package com.arainko.nawts.view.fragments.customizationFragment

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arainko.nawts.*
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.blendARGB
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.view.elements.NoteHolder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.bottom_sheet_customization_layout.*

class CustomizerFragment() : BottomSheetDialogFragment() {

    lateinit var fragmentBehavior: CustomizerFragmentBehavior
    lateinit var colorToButtonMap: Map<String, MaterialButton>

    constructor(
        modelActions: NoteViewModel,
        holder: NoteHolder,
        adapter: NoteAdapter
    ) : this() {
        fragmentBehavior =
            CustomizerFragmentBehavior(
                this,
                modelActions,
                holder,
                adapter
            )
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
        val checkmarkDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_color_check)

        customizerRoot.setBackgroundColor(fragmentBehavior
            .currentBackgroundColor
            .asIntColor()
            .blendARGB(Color.BLACK, 0.4f))

        colorToButtonMap = vibrantColors.union(pastelColors.toList())
            .mapIndexed { index, hexColor ->
                val parentContainer = if (index < vibrantColors.size) scrollContainerVibrant else scrollContainerPastel
                hexColor to layoutInflater
                    .inflate(R.layout.color_button_layout, parentContainer, false) as MaterialButton
            }.toMap()

        colorToButtonMap.forEach { (hexColor, button) ->
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

        colorToButtonMap[fragmentBehavior.currentBackgroundColor]?.icon = checkmarkDrawable
        colorToButtonMap[fragmentBehavior.currentStrokeColor]?.strokeWidth = 10

    }

    override fun onPause() {
        super.onPause()
        fragmentBehavior.commitNoteChanges()
        customizerRoot.tag?.let { (it as ValueAnimator).cancel() }
    }

}