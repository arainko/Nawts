package com.arainko.nawts.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.fragments.uiBehaviors.HomeFragmentBehavior
import com.arainko.nawts.view.NoteAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    lateinit var noteAdapter: NoteAdapter
    private lateinit var fragmentBehavior: HomeFragmentBehavior

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = HomeFragmentBehavior(this, model)
        noteAdapter = NoteAdapter(fragmentBehavior, fragmentBehavior)

        model.notes.observe(viewLifecycleOwner, Observer {
            noteAdapter.submitList(it)
        })

        fab.setOnClickListener(fragmentBehavior.fabOnClickListener)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }

        fragmentBehavior
            .recyclerViewSwipeToDismissListener
            .attachToRecyclerView(recyclerView)
    }


}
