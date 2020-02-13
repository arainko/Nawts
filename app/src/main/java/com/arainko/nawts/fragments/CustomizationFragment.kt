package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.R
import com.arainko.nawts.extensions.asColor
import com.arainko.nawts.fragments.uiBehaviors.CustomizationFragmentUIBehavior
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.fragments.uiBehaviors.abstracts.HolderBehavior
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.fragment_customization.*
import kotlinx.android.synthetic.main.fragment_customization.bottomSheet
import kotlinx.android.synthetic.main.fragment_customization.recyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class CustomizationFragment : Fragment() {

    val sheetBehavior by lazy { BottomSheetBehavior.from(bottomSheet) }
    private val model: NoteViewModel by viewModels()
    private val fragmentBehavior by lazy { CustomizationFragmentUIBehavior(this, model) }
    private val noteAdapter by lazy { NoteAdapter(fragmentBehavior) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_customization, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        model.notes.observe(this, Observer { noteAdapter.submitList(it.reversed()) })

        val colors = listOf(
                "#63b598", "#ce7d78", "#ea9e70", "#a48a9e", "#c6e1e8", "#648177", "#0d5ac1",
        "#f205e6", "#1c0365", "#14a9ad"
        )
        val buttons = colors.map { hex ->
            layoutInflater.inflate(
                R.layout.color_button_layout,
                scrollContainer,
                false
            ).apply { setBackgroundColor(hex.asColor()) } as Button
        }

        buttons.forEach { scrollContainer.addView(it) }

    }


}
