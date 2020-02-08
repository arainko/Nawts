package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.NoteAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), Toolbar.OnMenuItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val model: NoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val adapter = NoteAdapter(model)
        model.notes.observe(this, Observer<List<Note>> { adapter.submitList(it.reversed()) })

        return inflater.inflate(R.layout.fragment_main, container, false).apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            fabAdd.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToNoteEditFragment("", "")
                findNavController().navigate(action)
            }
            bottom_app_bar.setOnMenuItemClickListener(this@MainFragment)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.navCustomization -> {
            findNavController().navigate(R.id.action_mainFragment_to_customizationFragment)
            true
        }
        else -> false
    }

}
