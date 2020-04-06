package com.arainko.nawts.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.view.viewmodels.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.databinding.FragmentHomeBinding
import com.arainko.nawts.extensions.delegates.dataBinding
import com.arainko.nawts.view.abstracts.HolderBehavior
import com.arainko.nawts.view.elements.NoteAdapter
import com.arainko.nawts.view.elements.NoteHolder
import com.arainko.nawts.view.elements.StartDragListener
import com.arainko.nawts.view.viewmodels.HomeViewModel
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.recyclerView

class HomeFragment : Fragment(),
    HolderBehavior<NoteHolder>,
    Toolbar.OnMenuItemClickListener {

    private val noteViewModel: NoteViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding by dataBinding<FragmentHomeBinding>(R.layout.fragment_home)
    lateinit var noteAdapter: NoteAdapter

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
    ): View? = binding.run {
        viewmodel = homeViewModel
        lifecycleOwner = this@HomeFragment
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dismissListener = SwipeDragCallback(homeViewModel).callback
            .apply { attachToRecyclerView(binding.recyclerView) }
        noteAdapter = NoteAdapter(this) { viewHolder -> dismissListener.startDrag(viewHolder)  }
        binding.homeBottomAppBar.setOnMenuItemClickListener(this)

        noteViewModel.notes.observe(viewLifecycleOwner, Observer {
            noteAdapter.submitList(it)
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            postponeEnterTransition()
            addOnScrollListener(rvOnScrollListener)
            doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    private val rvOnScrollListener: RecyclerView.OnScrollListener
        get() = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when {
                    dy > 0 && binding.fab.visibility == View.VISIBLE -> homeViewModel.isVisible.value =
                        false
                    dy < 0 && binding.fab.visibility != View.VISIBLE -> homeViewModel.isVisible.value =
                        true
                }
            }

        }

    override fun onHolderClick(holder: NoteHolder) {
        val extras = FragmentNavigatorExtras(
            holder.itemView to holder.itemView.transitionName
        )
        val action =
            HomeFragmentDirections.actionToEditingFragment(
                holder.note,
                noteViewModel.maxOrder,
                holder.note.transitionName
            )
        binding.fab.hide()
        binding.homeBottomAppBar.performHide()
        findNavController().navigate(action, extras)
    }

    override fun onHolderLongClick(holder: NoteHolder): Boolean {
        val bottomSheet = CustomizerFragment().apply {
            arguments = Bundle().apply {
                putParcelable("note", holder.note)
                putInt("adapterPosition", holder.adapterPosition)
            }
        }
        bottomSheet.show(childFragmentManager, "customizer")
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.searchMenuItem -> {
            binding.fab.hide()
            val action = HomeFragmentDirections.actionToSearchFragment()
            val extras = FragmentNavigatorExtras(
                binding.homeBottomAppBar to binding.homeBottomAppBar.transitionName
            )
            findNavController().navigate(action, extras)
            true
        }
        else -> false
    }


}
