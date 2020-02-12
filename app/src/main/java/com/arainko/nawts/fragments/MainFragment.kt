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
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.StateManager
import com.arainko.nawts.extensions.asColor
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.fragment_customization.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.recyclerView

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    private val stateManager by lazy { StateManager(this, model) }
    private val noteAdapter by lazy { NoteAdapter(stateManager.currentFragmentState) }
    val sheetBehavior by lazy { BottomSheetBehavior.from(bottomSheet) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        model.notes.observe(this, Observer { noteAdapter.submitList(it.reversed()) })
        fab.setOnClickListener { stateManager.currentFragmentState = stateManager.customizationState }
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

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
