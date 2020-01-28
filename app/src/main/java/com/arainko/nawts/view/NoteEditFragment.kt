package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        fragment.testButton.setOnClickListener {
            val header = fragment.headerField.text.toString()
            val content = fragment.contentField.text.toString()
            model.addNote(header, content)
            fragment.findNavController().navigate(R.id.action_noteEditFragment_to_mainFragment)
        }
        return fragment
    }



}
