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

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val model: NoteViewModel = ViewModelProvider(this).get(
            NoteViewModel::class.java)
        val adapter = RecyclerAdapter()
        model.repository.getNotes().observe(this,
            Observer<List<Note>> { adapter.notes = it })
        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            fabAdd.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
                findNavController().navigate(action)
            }
        }
    }


}
