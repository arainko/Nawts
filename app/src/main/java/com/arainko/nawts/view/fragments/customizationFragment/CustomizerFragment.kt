package com.arainko.nawts.view.fragments.customizationFragment

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.arainko.nawts.*
import com.arainko.nawts.databinding.FragmentCustomizerBinding
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asIntColor
import com.arainko.nawts.extensions.blendARGB
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.view.fragments.homeFragment.HomeFragment
import com.arainko.nawts.view.viewmodels.CustomizerViewModel
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_customizer.*

class CustomizerFragment : BottomSheetDialogFragment() {

    val noteViewModel: NoteViewModel by viewModels()
    val customizerViewModel: CustomizerViewModel by viewModels()
    private val binding: FragmentCustomizerBinding by dataBinding(R.layout.fragment_customizer)

    val fragmentHandler: CustomizerFragmentHandler = CustomizerFragmentHandler(this)
    lateinit var colorToButtonMap: Map<String, MaterialButton>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.viewmodel = customizerViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vibrantColors = resources.getStringArray(R.array.vibrant_colors)
        val pastelColors = resources.getStringArray(R.array.pastel_colors)
        val checkmarkDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_color_check)

        // TODO: Migrate to BindingAdapter
        binding.customizerRoot.setBackgroundColor(fragmentHandler
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
                setOnClickListener(fragmentHandler.onColorButtonClickListener)
            }
        }

        colorToButtonMap.values.forEachIndexed { index, materialButton ->
            val parentContainer = if (index < vibrantColors.size) scrollContainerVibrant else scrollContainerPastel
            materialButton.addTo(parentContainer)
        }

        colorToButtonMap[fragmentHandler.currentBackgroundColor]?.icon = checkmarkDrawable

    }

    override fun onPause() {
        super.onPause()
        fragmentHandler.commitNoteChanges()
        customizerRoot.tag?.let { (it as ValueAnimator).cancel() }
    }

}