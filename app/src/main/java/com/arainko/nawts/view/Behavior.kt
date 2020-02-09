package com.arainko.nawts.view

import android.view.View
import androidx.navigation.Navigation
import com.arainko.nawts.fragments.MainFragmentDirections
import com.arainko.nawts.model.DatabaseActions
import com.arainko.nawts.persistence.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class Behavior(dbActions: DatabaseActions) : NoteHolderBehavior(dbActions) {

    override val onClick: (Note, View) -> Unit= { note, view ->
            val action =
                MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                    view.cardHeader.text.toString(),
                    view.cardText.text.toString(),
                    note.id
                )
            Navigation.findNavController(view).navigate(action)
        }

    override val onLongClick: (Note, View) -> Boolean = { note, view ->
        dbActions.deleteAction(id = note.id)
        false
    }
}
