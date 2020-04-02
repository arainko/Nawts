package com.arainko.nawts.view.fragments.homeFragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.databinding.FragmentHomeBinding
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.viewmodels.HomeViewModel
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView


class HomeFragment : Fragment() {

    val noteViewModel: NoteViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding by dataBinding<FragmentHomeBinding>(R.layout.fragment_home)
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
    ): View? {
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBehavior = HomeFragmentBehavior(this, noteViewModel)
//        val fabAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
//        fab.startAnimation(fabAnim)
        noteAdapter = NoteAdapter(fragmentBehavior, fragmentBehavior)
        homeBottomAppBar.setOnMenuItemClickListener(fragmentBehavior.bottomBarListener)

        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE) {
                    homeViewModel.isVisible.value = false
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    homeViewModel.isVisible.value = true
                }
            }
        }

        recyclerView.addOnScrollListener(listener)

        noteViewModel.notes.observe(viewLifecycleOwner, Observer {
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

    override fun onPause() {
        super.onPause()
        Log.d("TEST", "PAUSED")
    }

}
