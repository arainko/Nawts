package com.arainko.nawts.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arainko.nawts.R
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.persistence.Note
import com.arainko.nawts.view.HolderBehavior
import com.arainko.nawts.view.NoteAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class CustomizationFragment : Fragment(), HolderBehavior<Note> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val model: NoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val adapter = NoteAdapter(this)
        model.notes.observe(this, Observer<List<Note>> { adapter.submitList(it.reversed()) })

        return inflater.inflate(R.layout.fragment_customization, container, false).apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
    }

    override val onClick: (Note, View) -> Unit
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val onLongClick: (Note, View) -> Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


}
