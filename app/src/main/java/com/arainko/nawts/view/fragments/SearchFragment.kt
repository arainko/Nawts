package com.arainko.nawts.view.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.R
import com.arainko.nawts.databinding.FragmentSearchBinding
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.elements.NoteHolder
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), HolderBehavior<NoteHolder> {

    private val noteViewModel: NoteViewModel by activityViewModels()
    private val binding: FragmentSearchBinding by dataBinding(R.layout.fragment_search)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
            duration = resources.getInteger(R.integer.animationSpeed).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.run {
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteAdapter = NoteAdapter(this) {  }
        binding.searchView.setQuery("sho", true)
        noteViewModel.notes.observe(viewLifecycleOwner, Observer { notes ->
            val displayedNotes = if (binding.searchView.query.isBlank()) listOf() else
                notes.filter { it.header.contains(binding.searchView.query) || it.content.contains(binding.searchView.query) }
            noteAdapter.submitList(displayedNotes)
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            postponeEnterTransition()
            doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    override fun onHolderClick(holder: NoteHolder) {
        TODO("Not yet implemented")
    }

    override fun onHolderLongClick(holder: NoteHolder): Boolean {
        TODO("Not yet implemented")
    }

}
