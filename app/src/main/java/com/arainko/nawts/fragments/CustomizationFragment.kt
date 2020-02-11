package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.R
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.HolderBehavior
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        model.notes.observe(this, Observer<List<Note>> { adapter.submitList(it.reversed()) })
    }

    override fun onHolderClick(holderItem: Note, view: View) {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
