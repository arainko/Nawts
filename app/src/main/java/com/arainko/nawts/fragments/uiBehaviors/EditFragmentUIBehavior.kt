package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.R
import com.arainko.nawts.extensions.hideKeyboard
import com.arainko.nawts.fragments.EditFragment
import com.arainko.nawts.fragments.EditFragmentArgs
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.entities.NoteStyle
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragmentUIBehavior(
    fragment: EditFragment,
    private val model: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentUIBehavior<EditFragment>(fragment),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val note = args.note.apply {
            header = fragment.headerField.text.toString()
            content = fragment.contentField.text.toString()
        }
        if (note.id != 0) model.updateNote(note) else model.addNote(note)
        fragment.headerField.hideKeyboard()
        fragment.contentField.hideKeyboard()
        findNavController(fragment).navigate(R.id.action_global_mainFragment)
    }
}