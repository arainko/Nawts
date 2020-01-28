package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arainko.nawts.NoteViewModel
import com.arainko.nawts.R
import com.arainko.nawts.persistence.NoteDao
import com.arainko.nawts.persistence.Repository
import kotlinx.android.synthetic.main.fragment_note_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class NoteEditFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment = inflater.inflate(R.layout.fragment_note_edit, container, false)
        val model = ViewModelProvider(this).get(NoteViewModel::class.java)
        fragment.testButton.setOnClickListener { model.testAdd() }
        return fragment
    }



}
