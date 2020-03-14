package com.arainko.nawts.view.containters


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.view.control.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.blendARGB
import com.arainko.nawts.view.control.HomeFragmentBehavior
import com.arainko.nawts.view.elements.NoteAdapter
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView


class HomeFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    lateinit var noteAdapter: NoteAdapter
    private lateinit var fragmentBehavior: HomeFragmentBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = HomeFragmentBehavior(this, model)
        noteAdapter = NoteAdapter(fragmentBehavior, fragmentBehavior)
        fab.backgroundTintList = ColorStateList.valueOf(
            "#FFFFFF".asIntColor()
                .blendARGB(Color.BLACK, 0.2f)
                .blendARGB(Color.WHITE, 0.4f)
        )

        model.notes.observe(viewLifecycleOwner, Observer {
            noteAdapter.submitList(it)
        })

        fab.setOnClickListener(fragmentBehavior.fabOnClickListener)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }

        fragmentBehavior
            .recyclerViewSwipeToDismissListener
            .attachToRecyclerView(recyclerView)


    }


}
