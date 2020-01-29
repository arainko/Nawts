package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val model: NoteViewModel = ViewModelProvider(this).get(
            NoteViewModel::class.java)
        val adapter = RecyclerAdapter()
        val action = MainFragmentDirections.actionMainFragmentToNoteEditFragment("header", "content", 2)
        view.recyclerView.layoutManager = LinearLayoutManager(this.context)
        view.recyclerView.adapter = adapter

        model.repository.getNotes().observe(this,
            Observer<List<Note>> { adapter.notes = it })

        view.fabAdd.setOnClickListener {
            view.findNavController().navigate(action)
        }
        return view
    }


}
