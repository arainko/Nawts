package com.arainko.nawts.view.fragments.homeFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.dataBindings
import com.arainko.nawts.databinding.FragmentHomeBinding
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.viewmodels.HomeViewModel
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView


class HomeFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding by dataBindings<FragmentHomeBinding>(R.layout.fragment_home)
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
                    fab.hide();
                    homeBottomAppBar.performHide()
                    homeViewModel.isHidden = View.INVISIBLE
                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
                    fab.show();
                    homeBottomAppBar.performShow()
                    homeViewModel.isHidden = View.VISIBLE
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
}
