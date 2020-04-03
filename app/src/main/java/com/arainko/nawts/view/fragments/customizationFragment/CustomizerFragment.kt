package com.arainko.nawts.view.fragments.customizationFragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.arainko.nawts.R
import com.arainko.nawts.databinding.FragmentCustomizerBinding
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.view.fragments.homeFragment.HomeFragment
import com.arainko.nawts.view.viewmodels.CustomizerViewModel
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton


class CustomizerFragment : BottomSheetDialogFragment() {

    private val noteViewModel: NoteViewModel by activityViewModels()
    private val customizerViewModel: CustomizerViewModel by viewModels()
    private val binding: FragmentCustomizerBinding by dataBinding(R.layout.fragment_customizer)
    lateinit var colorToButtonMap: Map<String, MaterialButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.run {
        viewmodel = customizerViewModel.apply {
            note = arguments?.getParcelable("note")!!
            noteAdapterPosition = arguments?.getInt("adapterPosition")!!
            backgroundColor.value = note.style.backgroundColor
        }
        lifecycleOwner = this@CustomizerFragment
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vibrantColors = resources.getStringArray(R.array.vibrant_colors)
        val pastelColors = resources.getStringArray(R.array.pastel_colors)
        val checkmarkDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_color_check)

        colorToButtonMap = vibrantColors.union(pastelColors.toList())
            .mapIndexed { index, hexColor ->
                val parentContainer =
                    if (index < vibrantColors.size) binding.scrollContainerVibrant else binding.scrollContainerPastel
                hexColor to (layoutInflater
                    .inflate(
                        R.layout.color_button_layout,
                        parentContainer,
                        false
                    ) as MaterialButton).apply {
                    tag = hexColor
                    setBackgroundColor(hexColor.asIntColor())
                    setOnClickListener {
                        customizerViewModel.onColorButtonClick(
                            colorToButtonMap,
                            checkmarkDrawable,
                            hexColor
                        )
                    }
                    addTo(parentContainer)
                }
            }.toMap()

        colorToButtonMap[customizerViewModel.backgroundColor.value]?.icon = checkmarkDrawable

    }

    override fun onPause() {
        super.onPause()
        customizerViewModel.note.run {
            style.backgroundColor = customizerViewModel.backgroundColor.value!!
            noteViewModel.updateNotes(this)
        }
        (parentFragment as HomeFragment)
            .noteAdapter
            .notifyItemChanged(customizerViewModel.noteAdapterPosition)
        binding.customizerRoot.tag?.let { (it as ValueAnimator).cancel() }
    }

}