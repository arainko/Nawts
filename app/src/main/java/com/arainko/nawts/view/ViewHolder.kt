package com.arainko.nawts.view

import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.arainko.nawts.fragments.MainFragmentDirections
import com.arainko.nawts.model.DatabaseActions
import kotlinx.android.synthetic.main.note_layout.view.*
import kotlin.properties.Delegates

class ViewHolder(itemView: View, private val dbActions: DatabaseActions) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener,
    View.OnLongClickListener {

    var noteId: Int by Delegates.notNull()
    val noteContent: TextView = itemView.cardText
    val noteHeader: TextView = itemView.cardHeader
    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
        val action =
            MainFragmentDirections.actionMainFragmentToNoteEditFragment(
                noteHeader.text.toString(),
                noteContent.text.toString(),
                noteId
            )
        Navigation.findNavController(view).navigate(action)
    }

    override fun onLongClick(view: View?): Boolean {
        dbActions.deleteAction(id = noteId)
        return false
    }

}