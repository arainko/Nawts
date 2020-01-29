package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import kotlinx.android.synthetic.main.fragment_note_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class NoteEditFragment : Fragment() {

    val args: NoteEditFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragment = inflater.inflate(R.layout.fragment_note_edit, container, false)
        val model = ViewModelProvider(this).get(NoteViewModel::class.java)
        fragment.headerField.setText(args.header)
        fragment.contentField.setText(args.content)

        fragment.testButton.setOnClickListener {
            val header = fragment.headerField.text.toString()
            val content = fragment.contentField.text.toString()
            model.updateNote(header, content)
            fragment.findNavController().navigate(R.id.action_noteEditFragment_to_mainFragment)
        }
        return fragment
    }



}
