package com.arainko.nawts.fragments


import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.R
import com.arainko.nawts.extensions.asColor
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.HolderBehavior
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.bottom_sheet_customization.view.*
import kotlinx.android.synthetic.main.fragment_customization.*
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class CustomizationFragment : Fragment(),
    HolderBehavior<Note> {

    private val model: NoteViewModel by viewModels()
    private val sheetBehavior by lazy { BottomSheetBehavior.from(bottomSheet) }
    private val adapter = NoteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_customization, container, false).apply {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        model.notes.observe(this, Observer { adapter.submitList(it.reversed()) })

        val colors = listOf("#63b598", "#ce7d78", "#ea9e70", "#a48a9e", "#c6e1e8", "#648177" ,"#0d5ac1" ,
            "#f205e6" ,"#1c0365" ,"#14a9ad")

        repeat(colors.size) {
            val button = layoutInflater.inflate(
                R.layout.color_button_layout,
                scrollContainer,
                false
            ) as Button
            button.setBackgroundColor(colors[it].asColor())
            scrollContainer.addView(button)
        }

        val children = scrollContainer

        Log.d("CHILDREN BUTTONS", "${children}")

    }

    override fun onHolderClick(holderItem: Note, view: View) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
