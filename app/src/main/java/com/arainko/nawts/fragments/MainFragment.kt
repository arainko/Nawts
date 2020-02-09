package com.arainko.nawts.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.NoteAdapter
import com.arainko.nawts.view.HolderBehavior
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.note_layout.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), HolderBehavior<Note> {

    private val model: NoteViewModel by lazy { ViewModelProvider(this).get(NoteViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val adapter = NoteAdapter(this)
        model.notes.observe(this, Observer<List<Note>> { adapter.submitList(it) })
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            fabAdd.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
                findNavController().navigate(action)
            }
        }
    }

    override val onClick: (Note, View) -> Unit= { note, view ->
        val action =
            MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                view.cardHeader.text.toString(),
                view.cardText.text.toString(),
                note.id
            )
        Navigation.findNavController(view).navigate(action)
    }

    override val onLongClick: (Note, View) -> Boolean = { note, _ ->
        model.deleteAction(id = note.id)
        false
    }
}
