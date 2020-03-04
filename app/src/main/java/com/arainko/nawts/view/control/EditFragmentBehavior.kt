package com.arainko.nawts.view.control

import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navOptions
import com.arainko.nawts.R
import com.arainko.nawts.hideKeyboard
import com.arainko.nawts.view.containters.EditFragment
import com.arainko.nawts.view.abstracts.FragmentUIBehavior
import com.arainko.nawts.view.containters.EditFragmentArgs
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragmentBehavior(
    fragment: EditFragment,
    private val modelActions: NoteViewModel,
    private val args: EditFragmentArgs
) : FragmentUIBehavior<EditFragment>(fragment),
    View.OnClickListener {

    override fun onClick(view: View?) {
        val newPos = args.itemCount
        val note = args.note.apply {
            header = fragment.headerField.text.toString()
            content = fragment.contentField.text.toString()
        }
        if (note.id != 0) modelActions.updateNote(note) else modelActions.addNote(note.apply { listOrder = newPos})
        fragment.headerField.hideKeyboard()
        fragment.contentField.hideKeyboard()
        fragment.fabSave.hide()
        findNavController(fragment).navigateUp()
    }
}