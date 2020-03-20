package com.arainko.nawts.view.containters.homeFragment


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.view.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.asIntColor
import com.arainko.nawts.blendARGB
import com.arainko.nawts.view.elements.NoteAdapter
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView


class HomeFragment : Fragment() {

    private val model: NoteViewModel by viewModels()
    lateinit var noteAdapter: NoteAdapter
    private lateinit var fragmentBehavior: HomeFragmentBehavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.animationSpeed).toLong()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = HomeFragmentBehavior(this, model)
//        val fabAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
//        fab.startAnimation(fabAnim)
        noteAdapter = NoteAdapter(fragmentBehavior, fragmentBehavior)

//

        model.notes.observe(viewLifecycleOwner, Observer {
            noteAdapter.submitList(it)
        })

        fab.setOnClickListener(fragmentBehavior.fabOnClickListener)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            postponeEnterTransition()
            doOnPreDraw { startPostponedEnterTransition() }
        }

        fragmentBehavior
            .recyclerViewSwipeToDismissListener
            .attachToRecyclerView(recyclerView)


    }


}
