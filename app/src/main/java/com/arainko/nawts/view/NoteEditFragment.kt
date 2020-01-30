package com.arainko.nawts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arainko.nawts.model.NoteViewModel
import com.arainko.nawts.R
import kotlinx.android.synthetic.main.fragment_note_edit.*
import kotlinx.android.synthetic.main.fragment_note_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class NoteEditFragment : Fragment(), View.OnClickListener {

    private val args: NoteEditFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_edit, container, false).apply {
            headerField.setText(args.header)
            contentField.setText(args.content)
            fabSave.setOnClickListener(this@NoteEditFragment)
        }
    }

    override fun onClick(view: View?) {
        val model = ViewModelProvider(this).get(NoteViewModel::class.java)
        val header = this.headerField.text.toString()
        val content = this.contentField.text.toString()
        val id = args.databaseId
        model.updateNote(header, content, id)
        this.findNavController().navigate(R.id.action_noteEditFragment_to_mainFragment)
    }


}
