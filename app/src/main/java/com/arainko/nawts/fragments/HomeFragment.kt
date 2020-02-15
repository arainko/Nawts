package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.persistence.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.extensions.addTo
import com.arainko.nawts.extensions.asColor
import com.arainko.nawts.extensions.makeToast
import com.arainko.nawts.fragments.uiBehaviors.HomeFragmentUIBehavior
import com.arainko.nawts.fragments.uiBehaviors.NoteCustomizer
import com.arainko.nawts.view.NoteAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_customization.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.bottomSheet
import kotlinx.android.synthetic.main.fragment_home.recyclerView

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    lateinit var sheetBehavior: BottomSheetBehavior<View>
    lateinit var noteAdapter: NoteAdapter
    private lateinit var fragmentBehavior: HomeFragmentUIBehavior

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = HomeFragmentUIBehavior(this, model)
        noteAdapter = NoteAdapter(fragmentBehavior)
        model.notes.observe(this, Observer { noteAdapter.submitList(it.reversed()) })
        fab.setOnClickListener(fragmentBehavior)

        sheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 300
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        fragmentBehavior.swiper.attachToRecyclerView(recyclerView)

        resources.getStringArray(R.array.colors).map { hex ->
            layoutInflater.inflate(R.layout.color_button_layout, scrollContainer, false).apply {
                tag = hex
                setBackgroundColor(hex.asColor())
                setOnClickListener {
                    NoteCustomizer.note.color = tag as String
                    model.updateNote(NoteCustomizer.note)
                }
            }.addTo(scrollContainer)
        }

    }

}
