package com.arainko.nawts.fragments


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
import com.arainko.nawts.extensions.hideKeyboard
import kotlinx.android.synthetic.main.fragment_note_edit.*
import kotlinx.android.synthetic.main.fragment_note_edit.view.*

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment(), View.OnClickListener {

    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_note_edit, container, false).apply {
            headerField.setText(args.header)
            contentField.setText(args.content)
            fabSave.setOnClickListener(this@EditFragment)
        }

    override fun onClick(view: View?) {
        val model = ViewModelProvider(this).get(NoteViewModel::class.java)
        val header = this.headerField.text.toString()
        val content = this.contentField.text.toString()
        val id = args.databaseId
        if (id != -1) model.updateNote(header, content, id) else model.addNote(header, content)
        this.run {
            headerField.hideKeyboard()
            contentField.hideKeyboard()
            findNavController().navigate(R.id.action_noteEditFragment_to_mainFragment)
        }
    }



}
