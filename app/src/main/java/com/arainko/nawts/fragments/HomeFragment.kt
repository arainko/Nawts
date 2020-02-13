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
import com.arainko.nawts.fragments.uiBehaviors.HomeFragmentUIBehavior
import com.arainko.nawts.view.NoteAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    private val fragmentBehavior by lazy { HomeFragmentUIBehavior(this, model) }
    private val noteAdapter by lazy { NoteAdapter(fragmentBehavior) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        model.notes.observe(this, Observer { noteAdapter.submitList(it.reversed()) })
        fab.setOnClickListener(fragmentBehavior)
    }

}
