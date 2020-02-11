package com.arainko.nawts.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.NoteAdapter
import com.arainko.nawts.view.HolderBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.note_layout.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(),
    HolderBehavior<Note>,
    View.OnClickListener {

    private val model: NoteViewModel by viewModels()
    private val noteAdapter = NoteAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false).apply {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
            setHasFixedSize(true)
        }
        model.notes.observe(this@MainFragment,
            Observer<List<Note>> { noteAdapter.submitList(it.reversed()) })
        fab.setOnClickListener(this@MainFragment)
    }

    // FAB's onClickListener
    override fun onClick(v: View?) = findNavController().navigate(
        MainFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
    )

    // RecyclerView's ViewHolder onClickListener
    override fun onHolderClick(holderItem: Note, view: View) {
        val action =
            MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                view.cardHeader.text.toString(),
                view.cardText.text.toString(),
                holderItem.id
            )
        Navigation.findNavController(view).navigate(action)
    }

    // RecyclerView's ViewHolder onLongClickListener
    override fun onHolderLongClick(holderItem: Note, view: View): Boolean {
        model.deleteAction(id = holderItem.id)
        return false
    }


}
