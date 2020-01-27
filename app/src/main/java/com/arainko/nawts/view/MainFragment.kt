package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.RecyclerAdapter
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val model: NoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val adapter = RecyclerAdapter()

        view.recyclerView.layoutManager = LinearLayoutManager(this.context)
        view.recyclerView.adapter = adapter

        model.repository.getNotes().observe(this,
            Observer<List<Note>> { adapter.notes = it })

        view.fabAdd.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_noteEditFragment)
        }
//        test()
        return view
    }

    fun test() {
        GlobalScope.launch {
            val model: NoteViewModel = ViewModelProvider(this@MainFragment).get(NoteViewModel::class.java)
            model.repository.insert(Note(1, "ASS", "ASS"))
            model.repository.insert(Note(2, "ASS", "ASS"))
            model.repository.insert(Note(3, "ASS", "ASS"))
        }
    }


}
