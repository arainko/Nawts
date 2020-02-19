package com.arainko.nawts.fragments.uiBehaviors

import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.arainko.nawts.R
import com.arainko.nawts.extensions.hideKeyboard
import com.arainko.nawts.fragments.EditFragment
import com.arainko.nawts.fragments.EditFragmentArgs
import com.arainko.nawts.fragments.uiBehaviors.abstracts.FragmentUIBehavior
import com.arainko.nawts.persistence.entities.Note
import com.arainko.nawts.persistence.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragmentUIBehavior(
    fragment: EditFragment,
    private val model: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentUIBehavior<EditFragment>(fragment),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val header = fragment.headerField.text.toString()
        val content = fragment.contentField.text.toString()
        val id = args.databaseId
        if (id != -1) model.updateNote(
            Note(
                header,
                content,
                id = id
            )
        )
            else model.addNote(
            Note(
                header,
                content
            )
        )
        this.run {
            fragment.headerField.hideKeyboard()
            fragment.contentField.hideKeyboard()
            findNavController(fragment).navigate(R.id.action_noteEditFragment_to_mainFragment)
        }
    }
}